const {} = require("http-proxy-middleware");
module.exports = function() {
  App.use(
    "/api",
    createProxyMiddleware({
      target: "http://localhost:8081",
      changeOrigin: true,  // 是否跨域
      secure: false
    })
  );
}