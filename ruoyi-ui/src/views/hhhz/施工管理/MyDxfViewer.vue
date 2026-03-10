<template>
  <div>
    <DxfViewer ref="viewer" :dxfUrl="dxfUrl" :fonts="fonts"
               @dxf-loaded="_OnLoaded" @dxf-cleared="_OnCleared" @dxf-message="_OnMessage"/>
  </div>
</template>

<script>
import DxfViewer from '@/views/hhhz/施工管理/DxfViewer.vue'
import { DxfViewer as _DxfViewer } from 'dxf-viewer'
/* import mainFont from '@/assets/fonts/Roboto-LightItalic.ttf'
import aux1Font from '@/assets/fonts/NotoSansDisplay-SemiCondensedLightItalic.ttf'
import aux2Font from '@/assets/fonts/HanaMinA.ttf'
import aux3Font from '@/assets/fonts/NanumGothic-Regular.ttf' */
var mainFont = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/Roboto-LightItalic.ttf'
var aux1Font = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NotoSansDisplay-SemiCondensedLightItalic.ttf'
var aux2Font = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/HanaMinA.ttf'
var aux3Font = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NanumGothic-Regular.ttf'
/* var mainFont, aux1Font, aux2Font, aux3Font
axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/Roboto-LightItalic.ttf').then((response) => {
  mainFont = response.data
})
axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NotoSansDisplay-SemiCondensedLightItalic.ttf').then((response) => {
  aux1Font = response.data
})
axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/HanaMinA.ttf').then((response) => {
  aux2Font = response.data
})
axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NanumGothic-Regular.ttf').then((response) => {
  aux2Font = response.data
}) */
export default {
  name: 'MyDxfViewer',
  components: { DxfViewer },

  props: {
    dxfUrl: {
      type: String
    }
  },

  data() {
    return {}
  },

  methods: {
    _OnLoaded() {
    },

    _OnCleared() {
    },

    _OnToggleLayer(layer, newState) {
    },

    _OnToggleAll(newState) {
    },

    _OnMessage(e) {
      let type = 'info'
      switch (e.detail.level) {
        case _DxfViewer.MessageLevel.WARN:
          type = 'warning'
          break
        case _DxfViewer.MessageLevel.ERROR:
          type = 'negative'
          break
      }
    }
  },

  created() {
    /* let mainFont, aux1Font, aux2Font, aux3Font
    axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/Roboto-LightItalic.ttf').then((response) => {
      mainFont = response.data
    })
    axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NotoSansDisplay-SemiCondensedLightItalic.ttf').then((response) => {
      aux1Font = response.data
    })
    axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/HanaMinA.ttf').then((response) => {
      aux2Font = response.data
    })
    axios.get('http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NanumGothic-Regular.ttf').then((response) => {
      aux2Font = response.data
    }) */
    /* console.log(mainFont)
    console.log(aux1Font)
    console.log(aux2Font)
    console.log(aux3Font) */
    /* mainFont = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/Roboto-LightItalic.ttf'
    aux1Font = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NotoSansDisplay-SemiCondensedLightItalic.ttf'
    aux2Font = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/HanaMinA.ttf'
    aux3Font = 'http://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/NanumGothic-Regular.ttf' */
    this.fonts = [mainFont, aux1Font, aux2Font, aux3Font]
    //this.fonts = [mainFont, aux1Font, aux3Font]
  }
}
</script>

<style scoped lang="less">

.root {
  .layersCol {
    border-left: #DBDBDB solid 1px;
  }
}

</style>
