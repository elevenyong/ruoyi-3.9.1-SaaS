import request from "@/utils/request";

// 获取测点列表
export function gCedianL(query) {
  return request({
    url: "/tMeasuringPoint/getPageList",
    method: "post",
    params: query,
  });
}

// 获取瓦斯日报
export function gWasiribaoL(query) {
  return request({
    //url: "/tSensorData/getDailySheetByMeasurePointId",
    url: "/tBKSensorData/getDrainageDayReport",
    method: "post",
    params: query,
  });
}
