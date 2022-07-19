<template>
  <div
    id="result"
    style="overflow: hidden; z-index: 100; top: 10%,
    position: absolute;
    top: 0;
    left: -15px;"
  >
    <td>
      <input
        id="txtSearch"
        class="sinput"
        type="text"
        v-model="keywords"
        placeholder="请输入输入关键词，例如：fastfood"
        style="width: 230px; height: 25px"
      />
    </td>
    <td width="120">
      <input
        type="button"
        :value="buttonText"
        class="btn"
        @click="passKeywords"
        style="width: 100px"
      />
    </td>
  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      keywords: "",
      buttonText: "点击开始搜索",
    };
  },
  methods: {
    passKeywords() {
      this.$parent.calculate(this.keywords);
    },
  },
};
</script>

<style>
.sinput {
  width: 120px;
  height: 20px;
  line-height: 20px;
  margin-left: 20px;
  padding-left: 5px;
}
.txt {
  color: #666666;
  font-weight: 600;
  font-size: 16px;
  padding-left: 20px;
}
</style>