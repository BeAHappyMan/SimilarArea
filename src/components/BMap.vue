<template>
  <!-- <div id="page"></div> -->
  <div id="allmap" style="overflow: hidden; zoom: 1; position: absolute">
    <Search v-on:calculate="calculate" ref="child" />
    <!-- <div v-show="!loading"><div> -->
    <div
      id="page"
      style="
        height: 70%;
        -webkit-transition: all 0.5s ease-in-out;
        transition: all 0.5s ease-in-out;
        position: absolute;
        top: 40px;
        left: 0;
      "
    ></div>
    <div
      style="
        overflow: hidden;
        position: absolute;
        z-index: 100;
        top: 15%;
        right: 10px;
      "
    >
      <p>
        <input
          type="button"
          value="清除框选区域"
          @click="clearMap()"
          class="btn"
        />
      </p>
    </div>
    <!-- <Search v-on:calculate="calculate" /> -->
    <div class="table">
      <log-table></log-table>
    </div>
  </div>
</template>

<script>
import BMap from "BMap";
import Search from "./Search.vue";
import request from "../utils/request";
import qs from "qs";
import LogTable from "./LogTable.vue";
export default {
  name: "BMap",
  components: {
    Search,
    LogTable,
  },
  data() {
    return {
      points: [], //储存矩形四个点的坐标
      keywords: "",
      map: null,
      overlays: [],
      drawingManager: null,
      styleOptions: {
        strokeColor: "red", //边线颜色。
        fillColor: "red", //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3, //边线的宽度，以像素为单位。
        strokeOpacity: 0.8, //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6, //填充的透明度，取值范围0 - 1。
        strokeStyle: "solid", //边线的样式，solid或dashed。
      },
    };
  },
  watch: {
    points: [], //储存矩形四个点的坐标
    overlays: [],
    res: [],
  },

  methods: {
    // testmethod(keywords){
    //   alert(keywords)
    //   console.log(keywords)
    // },
    // test(){
    //   request.get("/api/test",{
    //       params: {
    //         str : "test"
    //       },
    //     }).then((res) => {
    //       alert(res.str)
    //     })
    // },

    calculate(keywords) {
      //console.log(keywords);
      this.keywords = keywords;
      if (keywords === "") {
        alert("关键词不能为空！");
        return;
      }
      if (this.points.length === 0) {
        alert("请框选区域！！！");
        return;
      }
      this.$refs.child.buttonText = "正在计算中…………";
      // for(let i = 0; i < 4; i++){
      //   console.log(this.overlays[i].lat + "  " + this.overlays[i].lng)
      //   }
      //console.log(this.points[0]);
      request
        .get("/api/search", {
          params: {
            tags: [keywords],
            points: [
              this.points[0].lat,
              this.points[0].lng,
              this.points[2].lat,
              this.points[2].lng,
            ],
          },
          paramsSerializer: (params) => {
            return qs.stringify(params, { indices: false });
          },
        })
        .then((resp) => {
          //console.log(resp)
          if (resp.code == 200) {
            this.$refs.child.buttonText = "点击开始搜索";
            var lat1 = resp.data[0].lat;
            var lng1 = resp.data[0].lng;
            var lat2 = resp.data[1].lat;
            var lng2 = resp.data[1].lng;
            var secRing = [
              new BMap.Point(lng1, lat1),
              new BMap.Point(lng2, lat1),
              new BMap.Point(lng2, lat2),
              new BMap.Point(lng1, lat2),
            ]; //创建多边形
            //console.log(secRing)
            var secRingPolygon = new BMap.Polygon(secRing, {
              strokeColor: "blue",
              strokeWeight: 5,
              strokeOpacity: 0.5,
            });
            this.map.addOverlay(secRingPolygon);
            this.map.centerAndZoom(
              new BMap.Point((lng1 + lng2) / 2, (lat1 + lat2) / 2),
              this.map.getZoom()
            );
            this.overlays.push(secRingPolygon);
          }
        })
        .catch((err) => {
          console.log("请求失败：" + err.status + "," + err.statusText);
        });
    },
    overlaycomplete(e) {
      var overlay = e.overlay;
      // console.log(overlay, 1234);
      this.overlays.push(overlay);
      var markerMenu = new BMap.ContextMenu();
      markerMenu.addItem(
        new BMap.MenuItem("移除", () => {
          this.map.removeOverlay(overlay);
        })
      );
      overlay.addContextMenu(markerMenu);
    },

    rectanglecomplete(e, overlay) {
      if (this.overlays.length != 0) {
        this.clearMap();
      }
      this.points = overlay.getPath();
      // console.log(this.points);
      this.drawingManager.close();
    },

    clearMap() {
      for (var i = 0; i < this.overlays.length; i++) {
        this.map.removeOverlay(this.overlays[i]);
      }
      this.overlays.length = 0;
    },
    mapdata() {
      var map = new BMap.Map("page", { enableMapClick: false });
      this.map = map;
      map.centerAndZoom(new BMap.Point(119, 32), 10); // 初始化地图,设置中心点坐标和地图级别
      map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
      map.enableContinuousZoom(true); //启用地图惯性拖拽
      map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
      map.addControl(
        new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT })
      ); //向地图中添加比例尺控件

      //实例化鼠标绘制工具
      this.drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
          anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
          offset: new BMap.Size(5, 5), //偏离值
          drawingModes: [BMAP_DRAWING_RECTANGLE],
        },
        rectangleOptions: this.styleOptions, //矩形的样式
      });

      //添加鼠标绘制工具监听事件，用于获取绘制结果
      this.drawingManager.addEventListener(
        "overlaycomplete",
        this.overlaycomplete
      );
      this.drawingManager.addEventListener(
        "rectanglecomplete",
        this.rectanglecomplete
      );

      var size = new BMap.Size(80, 20);
      map.addControl(
        new BMap.CityListControl({
          anchor: BMAP_ANCHOR_TOP_LEFT,
          offset: size,
        })
      );

      var secRing = [
        new BMap.Point(118.5071, 32.291296),
        new BMap.Point(118.5071, 31.754266),

        new BMap.Point(119.053931, 31.754266),
        new BMap.Point(119.053931, 32.291296),
        new BMap.Point(118.5071, 32.291296),
      ]; //创建多边形
      //console.log(secRing)
      var secRingPolygon = new BMap.Polyline(secRing, {
        strokeColor: "red",
        strokeWeight: 5,
        strokeOpacity: 0,
        fillOpacity: 0,
      });
      secRingPolygon.disableMassClear();
      map.addOverlay(secRingPolygon);
    },
  },

  mounted() {
    this.mapdata();
  },
};
</script>

<style>
.table {
  width: 100%;
  height: 30%;
  position: absolute;
  /* overflow: scroll; */
  bottom: 0%;
}
.BMap_cpyCtrl {
  display: none;
}
.anchorBL {
  display: none;
}
#page {
  width: 100%;
  height: 70%;
  overflow: hidden;
  bottom: 25%;
}
#allmap {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.btn {
  background-color: #4caf50;
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 13px;
  width: 100px;
  height: 30px;
  line-height: 30px;
  text-align: center;
}
</style>