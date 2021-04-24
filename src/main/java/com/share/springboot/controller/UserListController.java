package com.share.springboot.controller;

import com.share.springboot.entity.UserList;
import com.share.springboot.service.AttentionService;
import com.share.springboot.service.UserListService;
import com.share.springboot.utils.MD5Util;
import com.share.springboot.utils.ValidateImageCodeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yu W
 * @date 2020/5/6 19:44
 */
@Controller
@RequestMapping("/user")
public class UserListController {
    @Autowired
    private UserListService userListService;
    @Autowired
    private AttentionService attentionService;
    /**
     * 注册方法
     * @param userList
     * @param code1
     * @param session
     * @return
     */
    @PostMapping("/toRegister")

    public String toRegister(UserList userList, String code1, HttpSession session,Model model){
        String code_session = (String) session.getAttribute("code1");
        //System.out.println(userList);
        if(code_session.equals(code1)){
            String pwd=	MD5Util.string2MD5(userList.getUser_password());
            userList.setUser_password(pwd);
            boolean success = userListService.insertAll(userList);
            if (success == true){
                model.addAttribute("err_msg","注册成功，请登录");
                return "login";
            }else{
                model.addAttribute("err_msg","注册失败，重新注册");
                return "login";
            }
        }else{
            model.addAttribute("err_msg","注册页面的验证码错误，重新注册");
            return "login";
        }

    }

    /**
     *
     * 登录方法
     * @param user_id
     * @param user_password
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/toLogin")
    public String toLogin(String user_id, String user_password, HttpSession session, Model model){
        /**
         * 使用shrio编写认证操作
         */
        UserList userList = userListService.selectByPhone(user_id);
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        String password = MD5Util.convertMD5(MD5Util.convertMD5(userList.getUser_password()));
        UsernamePasswordToken token = new UsernamePasswordToken(user_id,password);
        try {
            subject.login(token);
            String avatar_image = userListService.selectImage(user_id);
            session.setAttribute("username",userList.getUser_name());
            session.setAttribute("user_phone",user_id);
            session.setAttribute("avatar_image",avatar_image);
            session.setAttribute("user_motto",userList.getUser_motto());
            return "redirect:/index";
            //用户名不存在
        }catch (UnknownAccountException e){
            //e.printStackTrace();
            model.addAttribute("err_msg","找不到用户名");
            return "login";
            //密码错误
        }catch (IncorrectCredentialsException e){
            model.addAttribute("err_msg","用户名或者密码错误");
            return "login";
        }
//        if (userList==null){
//            model.addAttribute("err_msg","找不到用户名");
//            return "login";
//        }else if(!userList.getUser_password().equals(user_password)){
//            model.addAttribute("err_msg","用户名或者密码错误");
//            return "login";
//        }else{
//            String avatar_image = userListService.selectImage(user_id);
//            session.setAttribute("username",userList.getUser_name());
//            session.setAttribute("user_phone",user_id);
//            session.setAttribute("avatar_image",avatar_image);
//            session.setAttribute("user_motto",userList.getUser_motto());
//            return "index";
//        }
    }

    /**
     * 修改密码
     * @param user_code
     * @param user_id
     * @param code2
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/forgetPass")
    @ResponseBody
    public Map<String,String> toForget(String user_code, String user_id, String code2, Model model, HttpSession session){
        //获取此时验证码信息
        String code_session = (String) session.getAttribute("code2");
        System.out.println(user_code+user_id+code2);
        session.setAttribute("forget_phone",user_id);
        //map集合用来存放返回值
        Map<String,String> map = new HashMap<String, String>();
        //查询是否存在此用户
        UserList userList = userListService.selectByPhone(user_id);
        //判断是否验证码正确
        //判断邀请码是否正确
        if(!code_session.equals(code2)){
            map.put("result","error_code");
            return map;
        }else if(userList==null){
            map.put("result","error_user");
            return map;
        }else if(!userList.getUser_code().equals(user_code)){
            map.put("result","error_invite");
            return map;
        }else{
            map.put("result","success");
            return map;
        }
//        model.addAttribute("success_mes","success");

    }

    @PostMapping("/updatePass")
    @ResponseBody
    public Map<String,String> updatePass(String user_password,HttpSession session){
        String phone = (String)session.getAttribute("forget_phone");
        Map<String,String> map = new HashMap<String,String>();
        boolean flag = userListService.updatePassword(phone,user_password);
        if (flag == true){
            map.put("result","success");
            return map;
        }else{
            map.put("result","error_password");
            return map;
        }
    }

    @GetMapping("code1")
    public void getImage1(HttpSession session, HttpServletResponse response)throws IOException {
        //生成验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        session.setAttribute("code1",securityCode);//存入session域中
        //响应图片
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"png",outputStream);

    }
    @GetMapping("code2")
    public void getImage2(HttpSession session, HttpServletResponse response)throws IOException {
        //生成验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        session.setAttribute("code2",securityCode);//存入session域中
        //响应图片
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"png",outputStream);

    }
    @GetMapping("/toPersonInfo")
    public String toPersonInfo(Model model,HttpSession session){
        String user_phone = (String) session.getAttribute("user_phone");
        UserList userList = userListService.selectByPhone(user_phone);
        model.addAttribute("userList",userList);
//        System.out.println(userList);
        return "info";
    }

    @GetMapping("/toElseDetail/{id}")
    public String toElseDetail(@PathVariable(value ="id")String id,Model model,HttpSession session){
        String user_id =(String) session.getAttribute("user_phone");
        int flag = attentionService.selectExist(user_id,id);
        UserList elseList = userListService.selectByPhone(id);
        if(flag>0){
            int index = attentionService.selectAttention(user_id,id);
            elseList.setStatus(index);
        }else{
            elseList.setStatus(0);
        }
        model.addAttribute("elseList",elseList);
        session.setAttribute("else_user",elseList.getUser_id());
//        System.out.println(elseList);
        return "Objinfo";
    }


    @PostMapping("/updateUser")
    @ResponseBody
    public Map<String,String> updateUser(MultipartFile image_name,String user_name,
                                         String user_age,String user_motto, Model model, HttpSession session) throws IOException {
         Map<String,String> map = new HashMap<>();

        System.out.println(user_motto);
        String user_phone = (String) session.getAttribute("user_phone");
        int age = Integer.parseInt(user_age);
        UserList userList = userListService.selectByPhone(user_phone);
        if (image_name!=null) {
            //获取原始图片的拓展名
            String originalFilename = image_name.getOriginalFilename();
//            System.out.println(originalFilename);
            //新的文件名字
            String newFileName = UUID.randomUUID() + originalFilename;
            System.out.println(newFileName);
            //封装上传文件位置的全路径
//            String file_path ="/root/photo/";
            String file_path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/pictures/";
            String path = file_path + newFileName;
            // File targetFile = new File(file_path, newFileName);
            FileUtils.copyInputStreamToFile(image_name.getInputStream(), new File(file_path, newFileName));

            userList.setAvatar_image(newFileName);
            userList.setUser_name(user_name);
            userList.setUser_age(age);
            userList.setUser_motto(user_motto);
            userListService.updateOne(userList);
            String avatar_image = newFileName;
            session.setAttribute("avatar_image",avatar_image);
            map.put("result", "success");
        }else if(image_name==null){
            userList.setUser_name(user_name);
            userList.setUser_age(age);
            userList.setUser_motto(user_motto);
            userListService.updateOne(userList);
            map.put("result", "success");

        }else{
            map.put("result","error");

        }
        return map;
    }


}

