/**
 * Created by Administrator on 2021-01-26.
 */
var hbbtcustd, hbethustd;
var okvar={} ;
if ('WebSocket' in window) {
    websocketok = new WebSocket("ws://localhost:8080/test/ok");
}
else
{
    alert('Not support websocket')
}
websocketok.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.parse(event.data.replace('\\',''));
    var template = JSON.stringify(temp);
    var old = JSON.parse(template);
    //var old=temp;
    if(temp.symbol=="MCO-BTC")
    {
        console.log("1111");
    }
    saveasksdo('ok',old);
    savebidsdo('ok',old);
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC')-1);
            if('undefined'==typeof (okvar[bz])) {
                okvar[bz] = {};
            }
            var symbol =temp.symbol;
            okvar[bz][symbol]={};
            tempok=oktemprmb(temp,'BTC')
            okvar[bz][symbol].asks= tempok.asks;
            okvar[bz][symbol].bids= tempok.bids;
            putask1to('ok',bz,'BTC',tempok.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('-','');
            var result=cacule('ok',symbol,tempok.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(old,bz,'ok',symbol,tempok.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT')-1);
            if('undefined'==typeof (okvar[bz])) {
                okvar[bz] = {};
            }
            var symbol =temp.symbol;
            okvar[bz][symbol]={};
            tempok=oktemprmb(temp,'USDT')
            okvar[bz][symbol].asks= tempok.asks;
            okvar[bz][symbol].bids= tempok.bids;
            putask1to('ok',bz,'USDT',tempok.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('-','');
            var result=cacule('ok',symbol,tempok.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(old,bz,'ok',symbol,tempok.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'ETH':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('ETH')-1);
            if('undefined'==typeof (okvar[bz])) {
                okvar[bz] = {};
            }
            var symbol =temp.symbol;
            okvar[bz][symbol]={};
            tempok=oktemprmb(temp,'ETH')
            okvar[bz][symbol].asks= tempok.asks;
            okvar[bz][symbol].bids= tempok.bids;
            putask1to('ok',bz,'ETH',tempok.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('-','');
            var result=cacule('ok',symbol,tempok.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(old,bz,'ok',symbol,tempok.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
    }
    //alert("111");
}

websocketok.onclose=function () {

}
window.onload=function () {
    // gethbprice("btcusdt");
    //clearInterval(t);
    // gethbprice("ethusdt");
    //clearInterval(t1);
}
