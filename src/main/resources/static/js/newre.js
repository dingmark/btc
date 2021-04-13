/**
 * Created by Administrator on 2021-03-15.
 */
var percent_base=0.09;
var revar={};
var asksvar={};
var asks={};
var hbbtc,hbeth,hbrmb;
var okbtc,oketh;
var mcbtc,mceth;
var btbtc,bteth;
var bnbtc,bneth;
var zbbtc,zbeth,zbqc;
var bsbtc,bscnc;
var kbbtc,kbeth,kbcnc;

websocketre = new WebSocket("ws://localhost:8080/test/re");
websocketre.onmessage=function(event)
{
    revar=JSON.parse(event.data);
     hbbtc=revar.hbbtcusdt*revar.bsusdtcnc;
     hbeth=revar.hbethusdt*revar.bsusdtcnc;
     hbrmb=revar.bsusdtcnc;
     //ok实时价格
    okbtc=revar.okbtcusdt*revar.bsusdtcnc;
    oketh=revar.okethusdt*revar.bsusdtcnc;
    //mc实时价格
    mcbtc=revar.mcbtcusdt*revar.bsusdtcnc;
    mceth=revar.mcethusdt*revar.bsusdtcnc;
    //bt实时价格
    btbtc=revar.bterbtcusdt*revar.bsusdtcnc;
    bteth=revar.bterethusdt*revar.bsusdtcnc;
    //bn
    bnbtc=revar.bnbtcusdt*revar.bsusdtcnc;
    bneth=revar.bnethusdt*revar.bsusdtcnc;
    //zb
    zbbtc=revar.zbbtcusdt*revar.zbusdtqc;
    zbeth=revar.zbethusdt*revar.zbusdtqc;
    zbqc=revar.zbusdtqc;
    //bs
    bsbtc=revar.bsbtcusdt*revar.bsusdtcnc;
    bscnc=revar.bsusdtcnc;
    //kb
    kbbtc=revar.kbbtcusdt*revar.bsusdtcnc;
    kbeth=revar.kbethusdt*revar.bsusdtcnc;
}
function hbtemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'ETH':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbeth;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbeth;
            }
            break;
    }

    return temp;
}

function oktemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*okbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*okbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'ETH':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*oketh;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*oketh;
            }
            break;
    }

    return temp;
}

function mctemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*mcbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*mcbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'ETH':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*mceth;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*mceth;
            }
            break;
    }

    return temp;
}

function bttemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*btbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*btbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'ETH':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*bteth;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*bteth;
            }
            break;
    }

    return temp;
}

function bntemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*bnbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*bnbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'ETH':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*bneth;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*bneth;
            }
            break;
    }

    return temp;
}
function zbtemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*zbbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*zbbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*zbqc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*zbqc;
            }
            break;
        case 'QC':

            break;
    }

    return temp;
}

function bstemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][1]=temp.asks[i][1]*bsbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][1]=temp.bids[i][1]*bsbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][1]=temp.asks[i][1]*bscnc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][1]=temp.bids[i][1]*bscnc;
            }
            break;
        case 'CNC':

            break;
    }

    return temp;
}

function kbtemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'BTC':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*kbbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*kbbtc;
            }
            break;
        case 'USDT':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'ETH':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*kbeth;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*kbeth;
            }
            break;
    }

    return temp;
}
function findmaxAsk(trade,bz,symbol,ask1) {
    if('undefined'==typeof (asks[trade]))
    {
        asks[trade]={};
    }
    if ('undefined'==typeof (asks[trade][bz]))
    {
        asks[trade][bz]={};
    }
    if('undefined'==typeof (asks[trade][bz][symbol]))
    {
        asks[trade][bz][symbol]={};//new Array();
        //asks[trade][bz][symbol][0]=ask1[0];
        //return;
    }
    //var i=asks[trade][bz][symbol].length;
    asks[trade][bz][symbol][0]=ask1[0];
}
function putask1to(trade,bz,base,ask1) {
    if ('undefined' == typeof (asksvar[bz])) {
        asksvar[bz] = {};//new Array();
    }
    if ('undefined' == typeof (asksvar[bz][trade])) {
        asksvar[bz][trade] = {};
    }
    //hb卖1价格进数组1
    //f(trade, bz, base, ask1);
    switch (trade) {
     case 'hb':
     f(trade, bz, base, ask1);
     break;
     case 'bn':
     f(trade, bz, base, ask1);
     break;
     case 'bt':
     f(trade, bz, base, ask1);
     break;
     case 'kb':
     f(trade, bz, base, ask1);
     break;
     case'mc':
     f(trade, bz, base, ask1);
     break;
     case'bs':
     f(trade, bz, base, ask1);
     break;
     case'ok':
     f(trade, bz, base, ask1);
     break;
     case 'zb':
     f(trade, bz, base, ask1);
     break
     }
    }
    function f(trade, bz, base, ask1) {
        if ('undefined' == typeof (asksvar[bz][trade][base])) {
            asksvar[bz][trade][base] = {};
        }
        asksvar[bz][trade][base] = ask1;
    }

    //找到币种的最大值。并且返回交易对和最大值
    function findmax (bz)
    {
        var maxvalue={"trade":"","base":"base","max":0};
        var temp=asksvar[bz];
        for (var o in temp)
        {
            for(var p in temp[o])
            {
                if(maxvalue.max<temp[o][p])
                {
                    maxvalue.trade=o;
                    maxvalue.bz=bz;
                    maxvalue.base=p;
                    maxvalue.max=temp[o][p];
                }
            }
        }
        return maxvalue;
    }
    function cacule(trade_now,symbol,symbol_now,max) {

        var percent=max.max/symbol_now-1;
        return result={"bz":max.bz,"trade_now":trade_now,"symbol":symbol,"buyprice":symbol_now,
            "percent":percent,"sell_trade":max.trade,
            "sell_symbol":max.bz+'-'+max.base,"sellprice":max.max};
    }
    function newedrawtable(old,bz,buytrade,buysymbol,buyprice,percent,selltrade,sellsymbol,sellprice) {
        if (bz == '') {
            return;
        }
        //1已经存在对应的买入卖出交易对 新数据过来要替换原来的数据
        if (!isExsit(old,bz, buytrade, buysymbol, buyprice, percent, selltrade, sellsymbol, sellprice)) {
            if (buytrade != selltrade || (buytrade == selltrade && buysymbol != sellsymbol.replace("-", ""))) {
                var htmlstr = "";
                var buttonhtml = "";
                var pophtml="";
                //生成pophtml
                pophtml=drawpop(old,buytrade,buysymbol,selltrade,sellsymbol);
                buttonhtml = '<button type="button" class="btn btn-warning" title="Popover title"' +
                    'data-container="body" data-toggle="popover" data-placement="right" data-html="true"' +
                    'data-content="'+pophtml+'">' +
                    bz +
                    '</button>';
                htmlstr += '<tr class="warning"><td>' + buttonhtml + '</td><td>' + percent + '</td><td>' + buytrade +
                    '</td><td>' + buysymbol + '</td><td>' + buyprice + '</td><td>' + selltrade + '</td>' +
                    '<td>' + sellsymbol + '</td><td>' + sellprice + '</td></tr>';
                /* if($("#table_content tr").length>0)
                 {
                     var index = findposition($("#table_content tr"), percent);
                     $("#table_content tr")[index].prepend(htmlstr);
                 }
                 else
                 {
                     $("#table_content").append(htmlstr);}
                 }*/
                //没有找到在指定位置添加行的操作。
                $("#table_content").append(htmlstr);
            }
            //2不存在的买入卖出交易对你 新数据过来要追加
        }
    }
    function isExsit(old,bz,buytrad,buysymbol,buyprice,percent,selltrade,sellsymbol,sellprice) {
        //第0列币种 2买入机构 3买入交易对 5卖出机构 6卖出交易对
        //console.log(i+"----bz"+bz+"-----buytrade"+buytrad);
        for(var i=0;i<$("tr").length;i++)
        {

            if($("tr")[i].children[0].innerText==bz&&$("tr")[i].children[2].innerText==buytrad
            &&$("tr")[i].children[3].innerText==buysymbol&&$("tr")[i].children[5].innerText==selltrade
                &&$("tr")[i].children[6].innerText==sellsymbol
            )
            {
                //如果已经存在则更新 买 卖明细
                //$("tr")[i].children[0].attributes[7].textContent=drawpop(old,buytrad,buysymbol,selltrade,sellsymbol);
                $("tr")[i].children[0].children[0].dataset.content=drawpop(old,buytrad,buysymbol,selltrade,sellsymbol);
                //改1获利比率 4买入单价 7卖出单价
                $("tr")[i].children[1].innerText=percent;
                $("tr")[i].children[4].innerText=buyprice;
                $("tr")[i].children[7].innerText=sellprice;
                return true;
            }
        }
        return false;
    }
    //找到table行插入的位置
function findposition(trs,percent)
{
    for (index=0;index<trs.length-1;index++)
    {
        if(trs[index].children[1].innerText<=percent)
        {
            return index;
            break;
        }
    }
    return index;
}

//画popovertable
function drawpop(old,buytrade,buysymbol,selltrade,sellsymbol)
{
  var html="";
  var askhtml="";
  var bidhtml="";
    sellsymbol=getasksymbol(selltrade,sellsymbol);
  var oldasks=saveasks[selltrade][sellsymbol];
  var oldbids=old.bids;
  var traskhtml="";
  var trbidhtml="";
  switch (buytrade) {
      case 'bn':
          for(i=0;i<oldbids.length;i++)
          {
              trbidhtml+="<tr><td>"+i+"</td><td>"+oldbids[i][0]+"</td><td>"+oldbids[i][1]+"</td><td>"+(oldbids[i][0]*oldbids[i][1])+"</td><tr>";
          }
          break;
      case 'bs':
          for(i=0;i<oldbids.length;i++)
          {
              trbidhtml+="<tr><td>"+i+"</td><td>"+oldbids[i][1]+"</td><td>"+oldbids[i][0]+"</td><td>"+(oldbids[i][0]*oldbids[i][1])+"</td><tr>";
          }
          break;
      case 'bt':
          for(i=0;i<oldbids.length;i++) {
              trbidhtml += "<tr><td>" + i + "</td><td>" + oldbids[i][0] + "</td><td>" + oldbids[i][1] + "</td><td>" + (oldbids[i][0] * oldbids[i][1]) + "</td><tr>";
          }
          break;
      case 'hb':
          for(i=0;i<oldbids.length;i++) {
              trbidhtml += "<tr><td>" + i + "</td><td>" + oldbids[i][0] + "</td><td>" + oldbids[i][1] + "</td><td>" + (oldbids[i][0] * oldbids[i][1]) + "</td><tr>";
          }
          break;
      case 'kb':
          for(i=0;i<oldbids.length;i++) {
              trbidhtml += "<tr><td>" + i + "</td><td>" + oldbids[i][0] + "</td><td>" + oldbids[i][1] + "</td><td>" + (oldbids[i][0] * oldbids[i][1]) + "</td><tr>";
          }
          break;
      case 'mc':
          for(i=0;i<oldbids.length;i++) {
              trbidhtml += "<tr><td>" + i + "</td><td>" + oldbids[i][0] + "</td><td>" + oldbids[i][1] + "</td><td>" + (oldbids[i][0] * oldbids[i][1]) + "</td><tr>";
          }
          break;
      case 'ok':
          for(i=0;i<oldbids.length;i++) {
              trbidhtml += "<tr><td>" + i + "</td><td>" + oldbids[i][0] + "</td><td>" + oldbids[i][1] + "</td><td>" + (oldbids[i][0] * oldbids[i][1]) + "</td><tr>";
          }
          break;
      case 'zb':
          for(i=0;i<oldbids.length;i++) {
              trbidhtml += "<tr><td>" + i + "</td><td>" + oldbids[i][0] + "</td><td>" + oldbids[i][1] + "</td><td>" + (oldbids[i][0] * oldbids[i][1]) + "</td><tr>";
          }
          break;
  }
    switch (selltrade) {
        case 'bn':
            for(i=0;i<oldasks.length;i++)
            {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'bs':
            for(i=0;i<oldasks.length;i++)
            {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][1]+"</td><td>"+oldasks[i][0]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'bt':
            for(i=0;i<oldasks.length;i++) {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'hb':
            for(i=0;i<oldasks.length;i++) {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'kb':
            for(i=0;i<oldasks.length;i++) {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'mc':
            for(i=0;i<oldasks.length;i++) {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'ok':
            for(i=0;i<oldasks.length;i++) {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
        case 'zb':
            for(i=0;i<oldasks.length;i++) {
                traskhtml+="<tr><td>"+i+"</td><td>"+oldasks[i][0]+"</td><td>"+oldasks[i][1]+"</td><td>"+(oldasks[i][0]*oldasks[i][1])+"</td><tr>";
            }
            break;
    }

  html="<html><div>"+selltrade+"</div><div>"+sellsymbol+"</div><table cellpadding='0' border='1'>"
      + "<tr><td>序号</td><td>卖出价格</td><td>卖出数量</td><td>总价</td></tr>"
      +traskhtml+
      "</table><div>"+buytrade+"</div><div>"+buysymbol+"</div><table cellpadding='0' border='1'>"
      + "<tr><td>序号</td><td>买入价格</td><td>买入数量</td><td>总价</td></tr>"
      +trbidhtml+"</table></html>";
  return html;
}
var saveasks={};
var savebids={};
function  saveasksdo(trade,temp) {
    if(typeof(saveasks[trade])=='undefined')
    {
        saveasks[trade]={};
    }
    symbol=temp.symbol.replace('_','-');
    if(typeof(saveasks[trade][symbol])=='undefined')
    {
        saveasks[trade][symbol]={};
    }
    saveasks[trade][symbol]=temp.asks;
}
function savebidsdo(trade,temp) {
    if(typeof(savebids[trade])=='undefined')
    {
        savebids[trade]={};
    }
    symbol=temp.symbol.replace('_','-');
    if(typeof(savebids[trade][symbol])=='undefined')
    {
        savebids[trade][symbol]={};
    }
    savebids[trade][symbol]=temp.bids;
}

function getasksymbol(trade,symbol)
{
 switch (trade)
 {
     case 'hb':
         return symbol.replace('-','');
         break;
     case 'bn':
        return symbol.replace('-','');
         break;
     case 'zb':
        return symbol.replace('-','')+'-DEPTH';
        break;
     case 'kb':
         return symbol;
         break;
     case 'mc':
         return symbol;
         break;
     case 'bt':
         return symbol;
         break;
     case 'bs':
         return symbol;
         break;
     case 'ok':
         return symbol;
         break;
 }
}



