package com.coachtam.tqt;

import com.coachtam.tqt.utils.jwt.JwtUtils;
import com.coachtam.tqt.utils.jwt.RsaUtils;
import com.coachtam.tqt.utils.jwt.UserInfo;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 〈
 * 1.先根据密钥生产公钥和私钥
 * 2.根据私钥加密得到jwt
 * 3.根据公钥解密得到userinfo
 * 〉
 *
 * @author coach tam
 * @email 327395128@qq.com
 * @create 2019/12/20
 * @since 1.0.0
 * 〈坚持灵活 灵活坚持〉
 */
public class JwtTest {

    private static final String pubKeyPath = "/Users/macbook/rsa/rsa.pub";

    private static final String priKeyPath = "/Users/macbook/rsa/rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;
    /**
//     * 注释@Before代码运行
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "tqt2020");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo("1", "jack"), privateKey, 30);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6IjEiLCJ1c2VybmFtZSI6ImphY2siLCJleHAiOjE1ODAxOTMzNDB9.VAKk70u0LD6hSEjwOiUhBu7llQ8gAkgjXqCVWHWuihgylTUJ2aEcxk5XOG21nqwG-gsrQ6HVY6tIRTEUoHQiPdf2Gt66qC0_ooQAGr1T4U1GKgOAAv0Qktd0l5Qx-pdZ9xOWKJqFrfS0mfl3MPa9vgqir3Lo3O4euEwafsHOhkY";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
