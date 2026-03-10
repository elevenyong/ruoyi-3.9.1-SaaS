<template>
  <div class="login">
    <div class="title-image">
      <div class="title-image-zh">
        矿井瓦斯多元信息数据中心
      </div>
      <div class="title-image-en">
        <!--Wise Drill - 3D inversion system for drill holes-->
        Drill Hole 3D Inversion Management System
      </div>
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h1 class="title">用 户 登 录 </h1>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入登录ID">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="请输入密码"
                  @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>

      <!-- 多租户：tenantCode（新增一行输入，不改既有样式） -->
      <el-form-item prop="tenantCode">
        <el-input
          v-model="loginForm.tenantCode"
          type="text"
          auto-complete="off"
          placeholder="请输入租户编码（tenantCode）"
        >
          <svg-icon slot="prefix" icon-class="dashboard" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item prop="code" v-if="captchaOnOff">
        <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
                  @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="large" type="primary" @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
      <!--  底部  -->
      <div class="el-login-footer">
        <span>Copyright © 郑州慧矿智能科技有限公司 </span>
      </div>
    </el-form>

  </div>
</template>

<script>
import { getCodeImg } from '@/api/login'
import Cookies from 'js-cookie'
import { decrypt, encrypt } from '@/utils/jsencrypt'

export default {
  name: 'Login',
  data() {
    return {
      codeUrl: '',
      loginForm: {
        username: '',
        password: '',
        rememberMe: false,
        code: '',
        uuid: '',
        tenantCode: ''
      },
      loginRules: {
        username: [
          { required: true, trigger: 'blur', message: ' ' }
        ],
        password: [
          { required: true, trigger: 'blur', message: ' ' }
        ],
        tenantCode: [
          { required: true, trigger: "blur", message: "请输入租户编码" }
        ]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: false,
      // 注册开关
      register: false,
      //redirect: undefined
      redirect: '/m1/user'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        console.log(route)
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    // this.getCode()
    this.getCookie()
        // 多租户：默认从本地缓存读取（不改变登录页布局）
    this.loginForm.tenantCode = localStorage.getItem('tenantCode') || 'default'
  },
  methods: {
    getCode() {
      // getCodeImg().then(res => {
      //   this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff
      //   if (this.captchaOnOff) {
      //     this.codeUrl = 'data:image/gif;base64,' + res.img
      //     this.loginForm.uuid = res.uuid
      //   }
      // })
    },
    getCookie() {
      const username = Cookies.get('username')
      const password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
        code: this.loginForm.code,
        uuid: this.loginForm.uuid,
        tenantCode: localStorage.getItem('tenantCode') || this.loginForm.tenantCode || 'default'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set('username', this.loginForm.username, { expires: 30 })
            Cookies.set('password', encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          // 记住 tenantCode（后续请求头会自动带）
          localStorage.setItem('tenantCode', this.loginForm.tenantCode || 'default')
          this.$store.dispatch('Login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' }).catch(() => {
            })
          }).catch(() => {
            this.loading = false
            if (this.captchaOnOff) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background-1.jpg");
  //background-image: url("../assets/images/bg-00.png");
  background-size: cover;
  background-position: center;
}

.title-image {

  .title-image-zh {
    background: linear-gradient(to right, #009FF3, #00E9D0);
    -webkit-background-clip: text;
    color: transparent;
    border-radius: 0px;
    font-size: 25pt;
    text-align: center;
    //background: #ffffff;
    //background-image: url("../assets/images/title-image-zh-b.png");
    background-size: contain;
    width: 632px;
    height: 45.3px;
    //width: 400px;
    position: absolute;
    right: 15vw;
    top: 24vh;
    //padding: 25px 25px 5px 25px;
  }

  .title-image-en {
    background: linear-gradient(to right, #009FF3, #00E9D0);
    -webkit-background-clip: text;
    color: transparent;
    text-align: center;
    font-size: 18pt;
    border-radius: 0px;
    //background: #ffffff;
    //background-image: url("../assets/images/title-image-en-b.png");
    background-size: contain;
    width: 632px;
    height: 29.5px;
    //width: 400px;
    position: absolute;
    right: 15vw;
    top: 29.2vh;
    //padding: 25px 25px 5px 25px;
  }
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #0188BF;
  font-size: 22pt;
  letter-spacing: 5px;
}

.login-form {
  border-radius: 0px;
  //background: #ffffff;
  background-image: url("../assets/images/border.png");
  background-size: cover;
  //width: 400px;
  width: 632px;
  height: 436px;
  //width: 25vw;
  //height: 17vw;
  //width: 20vw;
  //height: 13.8vw;
  position: absolute;
  right: 15vw;
  top: 35vh;
  //padding: 25px 25px 5px 25px;
  padding: 20px 0px 5px 0px;
  text-align: center;


  .el-input {
    height: 70px;
    width: 472px;
    background: rgba($color: #000000, $alpha: 0.2);
    margin-bottom: 15px;

    input {
      padding-left: 80px;
      font-size: 20pt;
      height: 70px;
      width: 472px;
      background: rgba($color: #105696, $alpha: 0.6);
      border: 4px solid #0E73BC;
      color: #bfbfbf;
    }
  }

  .el-checkbox {
    width: 26px;
    height: 26px;
    margin: 0px 0px 20px -450px;
    color: aliceblue;

    .el-checkbox__input {
      width: 26px;
      height: 26px;

      .el-checkbox__inner {
        width: 26px;
        height: 26px;
        background: rgba($color: #105696, $alpha: 0.6);
        border: 4px solid #0E73BC;

        .checkbox {
          width: 26px;
          height: 26px;
        }
      }

      ::after {
        border: 3px solid #6ebef8;
        border-left: 0;
        border-top: 0;
        top: -1px;
        height: 15px;
        width: 7px;
        left: 4px;
      }
    }


    .el-checkbox__label {
      display: inline-block;
      vertical-align: bottom;
      height: 22px;
      font-size: 15pt;
      color: #BFBFBF;
      letter-spacing: 1px;
    }
  }

  .el-button {
    height: 60px;
    background-color: #0BA1F8;
    //padding: 0 80px 0 80px;
    //margin-top: 10px;
    width: 472px;
    font-size: 18pt;
    letter-spacing: 0;
    text-align: justify;
    text-justify: distribute-all-lines;
    text-align-last: justify;
    padding: 0 160px 0 160px;

    .button {
      width: 472px;
      margin: 0 80px 0 80px;
      height: 60px;
    }
  }

  .input-icon {
    height: 78px;
    width: 28px;
    margin-left: 30px;
    color: #0E73BC;
  }
}

.el-form-item__error {
  padding-top: 0px;
  /*margin: 0 80px 0 80px;*/
  font-size: 13px;
}

.login-tip {
  font-size: 18px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 80px;
  text-align: right;
  color: #fff;
  font-family: Arial;
  //font-size: 12px;
  font-size: 17px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}
</style>
