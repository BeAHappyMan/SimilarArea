<template>
  <div>
    <input
      type="button"
      value="查询历史"
      class="btn"
      @click="getLog"
      style="width: 100px"
    />

    <el-table
      :data="myLog"
      style="width: 100%; height: 175px; overflow: scroll"
    >
      <el-table-column prop="tags" label="标签" width="180"> </el-table-column>
      <el-table-column prop="requestPoints" label="请求坐标" width="auto">
      </el-table-column>
      <el-table-column prop="returnPoints" label="返回坐标" width="auto">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import request from "../utils/request";

export default {
  name: "LogTable",
  data() {
    return {
      myLog: [],
    };
  },
  methods: {
    getLog() {
      request
        .get("/api/getLog")
        .then((resp) => {
          // 完善内容，1.返回选择区域和返回区域的中心坐标即可（在后端处理），2.tags的关键词想办法分隔开（用逗号），3.表格位置和字段名的完善
          if (resp.code == 200) {
            this.myLog = resp.data;
            for (var i = 0; i < this.myLog.length; ++i) {
              this.myLog[i].returnPoints =
                "(" +
                String(this.myLog[i].returnPoints[0].lng) +
                " , " +
                String(this.myLog[i].returnPoints[0].lat) +
                "), \n(" +
                String(this.myLog[i].returnPoints[1].lng) +
                " , " +
                String(this.myLog[i].returnPoints[1].lat) +
                ")";
              this.myLog[i].requestPoints =
                "(" +
                String(this.myLog[i].requestPoints[0].lng) +
                " , " +
                String(this.myLog[i].requestPoints[0].lat) +
                "), \n(" +
                String(this.myLog[i].requestPoints[1].lng) +
                " , " +
                String(this.myLog[i].requestPoints[1].lat) +
                ")";
            }
            console.log(this.myLog);
          }
        })
        .catch((err) => {
          console.log("请求失败：" + err.status + "," + err.statusText);
        });
    },
  },
  mounted() {
    this.getLog();
  },
};
</script>

<style>
</style>