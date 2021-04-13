/**
 * Created by Administrator on 2021-01-26.
 */
var bnvar={};
if ('WebSocket' in window) {
    websocketbian = new WebSocket("ws://localhost:8080/test/bn");
}
else
{
    alert('Not support websocket')
}
websocketbian.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.parse(event.data.replace('\\',''));
    var template = JSON.stringify(temp);
    var old = JSON.parse(template);
    saveasksdo('bn',old);
    savebidsdo('bn',old);
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC'));
            if('undefined'==typeof (bnvar[bz])) {
                bnvar[bz] = {};
            }
            var symbol =temp.symbol;
            bnvar[bz][symbol]={};
            temp=bntemprmb(temp,'BTC');
            bnvar[bz][symbol].asks= temp.asks;
            bnvar[bz][symbol].bids= temp.bids;
            putask1to('bn',bz,'BTC',temp.asks[0][0]);

            //找到这个币种下面卖1目前最大值
            var max=findmax(bz);
            var result=cacule('bn',symbol,temp.bids[0][0],max);
            //该币种买1与卖1最大值比较，计算利差
            if(result.percent>=percent_base)
            {
                newedrawtable(old,bz,'bn',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT'));
            if('undefined'==typeof (bnvar[bz])) {
                bnvar[bz] = {};
            }
            var symbol =temp.symbol;
            bnvar[bz][symbol]={};
            temp=bntemprmb(temp,'USDT');
            bnvar[bz][symbol].asks= temp.asks;
            bnvar[bz][symbol].bids= temp.bids;
            putask1to('bn',bz,'USDT',temp.asks[0][0]);
            //找到这个币种下面卖1目前最大值
            var max=findmax(bz);
            var result=cacule('bn',symbol,temp.bids[0][0],max);
            //该币种买1与卖1最大值比较，计算利差
            if(result.percent>=percent_base)
            {
                newedrawtable(old,bz,'bn',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'ETH':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('ETH'));
            if('undefined'==typeof (bnvar[bz])) {
                bnvar[bz] = {};
            }
            var symbol =temp.symbol;
            bnvar[bz][symbol]={};
            temp=bntemprmb(temp,'ETH');
            bnvar[bz][symbol].asks= temp.asks;
            bnvar[bz][symbol].bids= temp.bids;
            putask1to('bn',bz,'ETH',temp.asks[0][0]);
            //找到这个币种下面卖1目前最大值
            var max=findmax(bz);
            var result=cacule(old,'bn',symbol,temp.bids[0][0],max);
            //该币种买1与卖1最大值比较，计算利差
            if(result.percent>=percent_base)
            {
                newedrawtable(bz,'bn',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
    }

};

websocketbian.onclose=function () {

};