const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

// vue.config.js
module.exports = {
  /*
    Vue-cli3:
    Crashed when using Webpack `import()` #2463
    https://github.com/vuejs/vue-cli/issues/2463
   */
  // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  publicPath: "/suvenAdmin/",
  productionSourceMap: false,
  /*
  pages: {
    index: {
      entry: 'src/main.js',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
  },
  */
  configureWebpack: config => {
    //生产环境取消 console.log
    if (process.env.NODE_ENV === 'production') {
      config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
    }
  },
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@$', resolve('src'))
      .set('@api', resolve('src/api'))
      .set('@assets', resolve('src/assets'))
      .set('@comp', resolve('src/components'))
      .set('@views', resolve('src/views'))
      .set('@layout', resolve('src/layout'))
      .set('@static', resolve('src/static'))
  },

  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          /* less 变量覆盖，用于自定义 ant design 主题 */

          /*
          'primary-color': '#F5222D',
          'link-color': '#F5222D',
          'border-radius-base': '4px',
          */
        },
        javascriptEnabled: true,
      }
    }
  },

  devServer: {
    port: 2200,
    proxy: {
     'qqs/upload': {
        //target: 'http://192.168.1.238:8099',
        target: 'http://192.168.5.240:9010/qqs',
        //target: 'https://testymzj.gzqqs.com/qqs',
        ws: false,
        changeOrigin: true
      },
      '/qqs': {
        //target: 'http://192.168.1.238:8099', //请求本地 需要jeecg-boot后台项目
        target: 'http://192.168.5.240:9010/qqs',
        //target: 'https://testymzj.gzqqs.com/qqs', //测试环境
        ws: false,
        changeOrigin: true
      },
    }
  },

  lintOnSave: undefined
}