<template>
  <div class="sidebar-logo-container" :class="{ 'collapse': collapse }"
       :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logoNoText" class="sidebar-logo"/>
        <h1 v-else class="sidebar-title"
            :style="{ color: sideTheme === 'theme-dark' ? variables.logoTitleColor : variables.logoLightTitleColor }">{{
            title
          }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo"/>
        <h1 class="sidebar-title"
            :style="{ color: sideTheme === 'theme-dark' ? variables.logoTitleColor : variables.logoLightTitleColor }">{{
            title
          }} </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
import logoNoText from '@/assets/logo/logoNoText.png'
import logoImg from '@/assets/logo/logo.png'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  computed: {
    variables() {
      return variables
    },
    sideTheme() {
      return this.$store.state.settings.sideTheme
    }
  },
  data() {
    return {
      //title: '数字孪生智能抽采后台管理系统',
      title: '矿井瓦斯多元信息数据中心',
      logo: logoImg,
      logoNoText: logoNoText
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 159px;
  //height: 100px;
  //line-height: 50px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 75%;
      //height: 32px;
      vertical-align: middle;
      margin-right: 12px;
      margin-top: 20px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: rgb(111, 158, 205);
      font-weight: 600;
      //width: 170px;
      width: 133px;
      margin-left: 10px;
      margin-right: 10px;
      margin-top: 15px;
      line-height: 1.4;
      font-size: 15px;
      //margin-top: 10px;
      //line-height: 50px;
      //font-size: 20px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
