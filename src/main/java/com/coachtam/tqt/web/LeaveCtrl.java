package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Leave;
import com.coachtam.tqt.service.LeaveService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.vo.ResultVO;
import com.coachtam.tqt.vo.TaskVO;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:	请假
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-5 22:48:29
 */
@RequestMapping("/api/leave")
@RestController
public class LeaveCtrl {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;


    @GetMapping("/deploy")
    public ResultVO<Page> deploy()
    {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("baoxiao.bpmn").deploy();

        //通过RepositoryService -->查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        System.out.println("流程key:"+processDefinition.getKey());
        System.out.println("流程ID:"+processDefinition.getId());
        return ResultVO.success(null);
    }

    /**
     * 我的请假记录
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page result = leaveService.page(pageNo,pageSize,user.getUsername());
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Leave> list(@PathVariable("id") String id)
    {
        Leave leave = leaveService.findById(id);

        return ResultVO.success(leave);
    }


    @GetMapping("/all")
    public ResultVO<List<Leave>> getAll()
    {
        List<Leave> result = leaveService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        leaveService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Leave leave)
    {
        leaveService.update(leave);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Leave leave)
    {
        leaveService.save(leave);
        return ResultVO.success(null);
    }




    /**
     * 查询我的任务
     * @return
     */
    @GetMapping("/myTaskList")
    public List<TaskVO>  myTaskList(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //没有做认证提示，先去登陆
        List<Task> list = taskService.createTaskQuery().taskAssignee(user.getUsername()).orderByTaskCreateTime().desc().list();

        List<TaskVO> result = list.stream()
                //Task --> TaskVO
                .map(x ->
                        {
                            String assignee = x.getAssignee();
                            com.coachtam.tqt.entity.User userInfo = userService.findByUsername(assignee);

                            if(userInfo!=null&&userInfo.getUserInfo()!=null)
                            {
                                assignee = userInfo.getUserInfo().getName();
                            }
                            return new TaskVO(x.getId(), x.getName(), x.getDescription(), assignee,x.getProcessInstanceId());
                        })
                //将转好的数据封装到一个新list中
                .collect(Collectors.toList());

        return result;
    }
    /**
     * 查询请假详情
     * @param processInstanceId
     * @return
     */
    @GetMapping("/taskDetail/{processInstanceId}")
    public Leave taskDetail(@PathVariable("processInstanceId") String processInstanceId){
        Leave result = leaveService.findByProcessInstanceId(processInstanceId);
        com.coachtam.tqt.entity.User user = userService.findByUsername(result.getCreateBy());
        if(user!=null&&user.getUserInfo()!=null)
        {
            result.setCreateBy(user.getUserInfo().getName());
        }
        return result;
    }
    /**
     * 完成任务
     * @param taskId
     * @return
     */
    @PutMapping("/complete/{taskId}")
    public String complete(@PathVariable("taskId") String taskId, @RequestBody Leave leave){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        //完成该任务，并指定由他的上级来处理
        Map<String,Object> valMap = new HashMap<>(1);
        valMap.put("to",leave.getReviewer());
        //添加备注
        if(leave!=null&&leave.getComment()!=null&&!leave.getComment().isEmpty())
        {
            taskService.addComment(taskId,null,leave.getComment());
        }
        taskService.complete(taskId,valMap);
        return "ok";
    }
    @GetMapping("/comments/{taskId}")
    public List<Comment> getProcessComments(@PathVariable("taskId") String taskId) {
        List<Comment> historyCommnets = new ArrayList<>();
//         1) 获取流程实例的ID
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance pi =runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//       2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityType("userTask").list();
//       3）查询每个历史任务的批注
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<Comment> comments = taskService.getTaskComments(historytaskId);
            // 4）如果当前任务有批注信息，添加到集合中
            if(comments!=null && comments.size()>0){
                historyCommnets.addAll(comments);
            }
        }
//       5）返回
        return historyCommnets;
    }


    /**
     * @Note 读取流程资源
     */
    @RequestMapping(value = "/read-img/{processInstanceId}")
    public void readResource(@PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response)
            throws Exception {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();

        if( StringUtils.isEmpty(processInstanceId) == false)
        {
            getActivitiProccessImage(processInstanceId,response);
            //ProcessDiagramGenerator.generateDiagram(pde, "png", getRuntimeService().getActiveActivityIds(processInstanceId));
        }
//        else
//        {
//            // 通过接口读取
//            InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);
//
//            // 输出资源内容到相应对象
//            byte[] b = new byte[1024];
//            int len = -1;
//            while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
//                response.getOutputStream().write(b, 0, len);
//            }
//        }
    }


    /**
     * 获取流程图像，已执行节点和流程线高亮显示
     */
    public void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response)
    {
        //logger.info("[开始]-获取流程图图像");
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(pProcessInstanceId).singleResult();

            if (historicProcessInstance == null) {
                //throw new BusinessException("获取流程实例ID[" + pProcessInstanceId + "]对应的历史流程实例失败！");
            }
            else
            {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(pProcessInstanceId).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<String>();
                int index = 1;
                //logger.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());

                    //logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());

                // 已执行的线集合
                List<String> flowIds = new ArrayList<String>();
                // 获取流程走过的线 (getHighLightedFlows是下面的方法)
                flowIds = getHighLightedFlows(bpmnModel,processDefinition, historicActivityInstanceList);



                // 获取流程图图像字符流
                ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
                //配置字体
                InputStream imageStream = pec.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds,"宋体","宋体","宋体",null,2.0);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            //logger.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //logger.error("【异常】-获取流程图失败！" + e.getMessage());
            //throw new BusinessException("获取流程图失败！" + e.getMessage());
        }
    }

    public List<String> getHighLightedFlows(BpmnModel bpmnModel,ProcessDefinitionEntity processDefinitionEntity,List<HistoricActivityInstance> historicActivityInstances)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId

        for (int i = 0; i < historicActivityInstances.size() - 1; i++)
        {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());


            List<FlowNode> sameStartTimeNodes = new ArrayList<FlowNode>();// 用以保存后续开始时间相同的节点
            FlowNode sameActivityImpl1 = null;

            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);// 第一个节点
            HistoricActivityInstance activityImp2_ ;

            for(int k = i + 1 ; k <= historicActivityInstances.size() - 1; k++)
            {
                activityImp2_ = historicActivityInstances.get(k);// 后续第1个节点

                if ( activityImpl_.getActivityType().equals("userTask") && activityImp2_.getActivityType().equals("userTask") &&
                        df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))   ) //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                {

                }
                else
                {
                    sameActivityImpl1 = (FlowNode)bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());//找到紧跟在后面的一个节点
                    break;
                }

            }
            sameStartTimeNodes.add(sameActivityImpl1); // 将后面第一个节点放在时间相同节点的集合里
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++)
            {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点

                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))  )
                {// 如果第一个节点和第二个节点开始时间相同保存
                    FlowNode sameActivityImpl2 = (FlowNode)bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                }
                else
                {// 有不相同跳出循环
                    break;
                }
            }
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows() ; // 取出节点的所有出去的线

            for (SequenceFlow pvmTransition : pvmTransitions)
            {// 对所有的线进行遍历
                FlowNode pvmActivityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement( pvmTransition.getTargetRef());// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;

    }

}
