package com.buguagaoshu.homework.evaluation.controller;

import com.buguagaoshu.homework.common.domain.ResponseDetails;
import com.buguagaoshu.homework.common.enums.ReturnCodeEnum;
import com.buguagaoshu.homework.common.utils.PageUtils;
import com.buguagaoshu.homework.evaluation.config.BaseWebInfoConfig;
import com.buguagaoshu.homework.evaluation.config.TokenAuthenticationHelper;
import com.buguagaoshu.homework.evaluation.utils.JwtUtil;
import com.buguagaoshu.homework.evaluation.vo.AdminAddUser;
import com.buguagaoshu.homework.evaluation.entity.UserEntity;
import com.buguagaoshu.homework.evaluation.entity.UserRoleEntity;
import com.buguagaoshu.homework.evaluation.service.UserRoleService;
import com.buguagaoshu.homework.evaluation.service.UserService;
import com.buguagaoshu.homework.evaluation.vo.AlterUserStatus;
import com.buguagaoshu.homework.evaluation.vo.UserRoleInClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-06-03 23:46
 */
@RestController
public class UserController {
    private final UserService userService;

    private final UserRoleService userRoleService;

    private final BaseWebInfoConfig baseWebInfoConfig;

    @Autowired
    public UserController(UserService userService, UserRoleService userRoleService, BaseWebInfoConfig baseWebInfoConfig) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.baseWebInfoConfig = baseWebInfoConfig;
    }

    /**
     * 获取用户列表
     * 老师和助教可以获取用户列表，向课程导入学生
     * 但不能修改，老师可以课程列表里添加助教
     * */
    @GetMapping("/teacher/user/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        return userService.selectUserAndRoleList(params);
    }

    /**
     * 教师获取当前课程的学生列表
     * 只能获取自己班的
     * */
    @GetMapping("/teacher/user/classList")
    public ResponseDetails classList(@RequestParam Map<String, Object> params,
                               HttpServletRequest request) {
        PageUtils pageUtils = userService.selectClassUser(params,
                JwtUtil.getNowLoginUser(request, TokenAuthenticationHelper.SECRET_KEY));
        if (pageUtils != null) {
            return ResponseDetails.ok().put("page", pageUtils);
        }
        return ResponseDetails.ok(ReturnCodeEnum.NOO_FOUND);
    }

    /**
     * 教师将学生导入课程
     * */
    @PostMapping("/teacher/user/join")
    public ResponseDetails studentJoinClass(@RequestBody List<AdminAddUser> userList,
                                            HttpServletRequest request) {
        Map<String, String> map = userService.addStudentCurriculumRelationship(userList,
                JwtUtil.getNowLoginUser(request, TokenAuthenticationHelper.SECRET_KEY));
        if (map == null) {
            return ResponseDetails.ok(ReturnCodeEnum.NOO_FOUND.getCode(), "输入数据有误或没有权限!");
        }
        return ResponseDetails.ok().put("data", map);
    }


    /**
     * 添加用户
     * */
    @PostMapping("/admin/user/add")
    public ResponseDetails addOneUser(@RequestBody List<UserEntity> UserEntityList,
                                      HttpServletRequest request) {
         List<AdminAddUser> userList = userService.adminAddUser(UserEntityList, request);
         return ResponseDetails.ok().put("data", userList);
    }

    /**
     * 管理员重置密码
     * */
    @PostMapping("/admin/user/rest/password")
    public ResponseDetails restPassword(@RequestBody AdminAddUser adminAddUser) {

        adminAddUser = userService.restPassword(adminAddUser);
        return ResponseDetails.ok().put("data", adminAddUser);
    }


    /**
     * 角色修改
     * */
    @PostMapping("/admin/user/alter/role")
    public ResponseDetails alterUserRole(@RequestBody UserRoleEntity userRoleEntity,
                                         HttpServletRequest request) {
        ReturnCodeEnum returnCodeEnum = userRoleService.alterUserRole(userRoleEntity, request);
        return ResponseDetails.ok(returnCodeEnum.getCode(), returnCodeEnum.getMsg());
    }


    /**
     * 教师修改课程内的角色
     * */
    @PostMapping("/teacher/user/alter/role")
    public ResponseDetails teacherAlterCourseRole(@RequestBody UserRoleInClassVo userRoleInClassVo,
                                                  HttpServletRequest request) {
        ReturnCodeEnum returnCodeEnum = userRoleService.teacherAlterUserRole(userRoleInClassVo, request);
        return ResponseDetails.ok(returnCodeEnum);
    }


    @PostMapping("/admin/user/alter/status")
    public ResponseDetails alterUserStatus(@RequestBody AlterUserStatus alterUserStatus) {
        ReturnCodeEnum returnCodeEnum = userService.alterUserStatus(alterUserStatus);
        return ResponseDetails.ok(returnCodeEnum.getCode(), returnCodeEnum.getMsg());
    }


    @GetMapping("/loginOut")
    public ResponseDetails loginOut(HttpServletRequest request,
                                    HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, TokenAuthenticationHelper.COOKIE_TOKEN);
        if (cookie != null) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return ResponseDetails.ok();
        }
        return ResponseDetails.ok(ReturnCodeEnum.NOT_LOGGED_IN.getCode(), ReturnCodeEnum.NOT_LOGGED_IN.getMsg());
    }
}
