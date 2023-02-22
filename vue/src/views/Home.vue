<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid"></i>用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#67C23A">
          <div><i class="el-icon-money"></i>销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100,0000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #909339">
          <div><i class="el-icon-bank-card"></i>收益额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            30,0000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop"></i>门店总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            36
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="14">
        <div id="chart" style="width: 700px; height: 400px"></div>
      </el-col>
      <el-col :span="10">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="14">
        <div id="chart2" style="width: 700px; height: 400px"></div>
      </el-col>
      <el-col :span="10">
        <div id="pie2" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>
  </div>

</template>
<script>

import * as echarts from 'echarts'

export default {
  name: "Home",
  data(){
    return{

    }
  },
  // 在页面加载完成后，初始化图表
  mounted() {
    //折线图1
    var option = {
      title: {
        text: '各季度会员数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name:"星巴克",
          data: [],
          type: 'line'
        },
        {
          name:"星巴克",
          data: [],
          type: 'bar'
        },
        {
          name:"瑞幸咖啡",
          data: [],
          type: 'line'
        },
        {
          name:"瑞幸咖啡",
          data: [],
          type: 'bar'
        }
      ]
    };

    var chartDom = document.getElementById('chart');
    var myChart = echarts.init(chartDom);

    this.request.get("/echarts/members").then(res => {
      // 填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data
      option.series[2].data = [5,6,7,8]
      option.series[3].data = [5,6,7,8]
      // 数据准备完毕之后再set
      myChart.setOption(option);

    })

    // 饼图

    var pieOption = {
      title: {
        text: '各季度会员数量统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name:'星巴克',
          type: 'pie',
          radius: '50%',
          center: ['30%', '60%'],
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'inner', //标签的位置
              textStyle : {
                fontWeight : 300 ,
                fontSize : 14,    //文字的字体大小
                color: "#fff"
              },
              formatter:'{d}%'
            }
          },
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        {
          name:'瑞幸咖啡',
          type: 'pie',
          radius: '50%',
          center: ['80%', '60%'],
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'inner', //标签的位置
              textStyle : {
                fontWeight : 300 ,
                fontSize : 14,    //文字的字体大小
                color: "#fff"
              },
              formatter:'{d}%'
            }
          },
          data: [
            {name: "第一季度", value: 5},
            {name: "第二季度", value: 6},
            {name: "第三季度", value: 7},
            {name: "第四季度", value: 8},
          ],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    this.request.get("/echarts/members").then(res => {

      pieOption.series[0].data = [
        {name: "第一季度", value: res.data[0]},
        {name: "第二季度", value: res.data[1]},
        {name: "第三季度", value: res.data[2]},
        {name: "第四季度", value: res.data[3]},
      ]
      pieChart.setOption(pieOption)
    })

    //饼图2
    var pieDom2 = document.getElementById('pie2');
    var pieChart2 = echarts.init(pieDom2);
    var pieoption2 = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 40,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: [
            {value: 1048, name: 'Search Engine'},
            {value: 735, name: 'Direct'},
            {value: 580, name: 'Email'},
            {value: 484, name: 'Union Ads'},
            {value: 300, name: 'Video Ads'}
          ]
        }
      ]
    };
    pieChart2.setOption(pieoption2);

    //折线图2
    var chartDom2 = document.getElementById('chart2');
    var myChart2 = echarts.init(chartDom2);
    var chartoption2 = {
      title: {
        text: 'Stacked'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: 'Email',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
          name: 'Union Ads',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
          name: 'Video Ads',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: [150, 232, 201, 154, 190, 330, 410]
        },
        {
          name: 'Direct',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: [320, 332, 301, 334, 390, 330, 320]
        },
        {
          name: 'Search Engine',
          type: 'line',
          stack: 'Total',
          label: {
            show: true,
            position: 'top'
          },
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: [820, 932, 901, 934, 1290, 1330, 1320]
        }
      ]
    };
    myChart2.setOption(chartoption2);
  },


}
</script>
<style scoped>
</style>
