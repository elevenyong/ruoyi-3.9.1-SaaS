import axios from 'axios'
import { Loading, Message } from 'element-ui'
import * as url from 'url'
import da from 'element-ui/src/locale/lang/da'
import request from '@/utils/request'

let downloadLoadingInstance
const cadExportService = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: 'http://127.0.0.1:5000',
  //baseURL: 'http://192.168.3.129:5000',
  // 超时
  timeout: 10000
})

// 导出CAD图纸(dwg)
export function cadExport(data, filename) {
  downloadLoadingInstance = Loading.service({
    text: '正在下载数据，请稍候',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.7)'
  })
  return cadExportService.post('/CAD/export', data, {
    headers: { 'Content-Type': 'application/json' },
    responseType: 'blob'
  }).then(async(data) => {
    const blob = new Blob([data.data])
    saveAs(blob, filename)
    downloadLoadingInstance.close()
  }).catch((r) => {
    console.error(r)
    Message.error('下载文件出现错误，请联系管理员！')
    downloadLoadingInstance.close()
  })
}

// 获取CAD图纸数据(dxf)
export function cadExportDXF(data) {
  return cadExportService({
    url: '/CAD/export_dxf',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
