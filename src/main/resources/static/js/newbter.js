/**
 * Created by Administrator on 2021-01-26.
 */
var btvar={};
if ('WebSocket' in window) {
    websocketbter = new WebSocket("ws://"+getContextPath()+"/test/bt");
}
else
{
    alert('Not support websocket')
}
websocketbter.onmessage=function(event)
{
    var temp=JSON.parse(event.data.replace('\\',''));
    var template = JSON.stringify(temp);
    var old = JSON.parse(template);

    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    var rate=Getrate('bt',type);
    saveasksdo('bt',old,rate);
    savebidsdo('bt',old,rate);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC')-1);
            if('undefined'==typeof (btvar[bz])) {
                btvar[bz] = {};
            }
            var symbol =temp.symbol;
            btvar[bz][symbol]={};
            temp=bttemprmb(temp,'BTC');
            btvar[bz][symbol].asks= temp.asks;
            btvar[bz][symbol].bids= temp.bids;
            if(temp.asks.length==0||temp.bids.length==0)
            {
                console.log(symbol);
                return;
            }
            putask1to('bt',bz,'BTC',temp.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('_','');
            var result=cacule('bt',symbol,temp.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(old,temp,bz,'bt',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT')-1);
            if('undefined'==typeof (btvar[bz])) {
                btvar[bz] = {};
            }
            var symbol =temp.symbol;
            btvar[bz][symbol]={};
            temp=bttemprmb(temp,'USDT');
            btvar[bz][symbol].asks= temp.asks;
            btvar[bz][symbol].bids= temp.bids;
            if(temp.asks.length==0||temp.bids.length==0)
            {
                console.log(symbol);
                return;
            }
            putask1to('bt',bz,'USDT',temp.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('_','');
            var result=cacule('bt',symbol,temp.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(old,temp,bz,'bt',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'ETH':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('ETH')-1);
            if('undefined'==typeof (btvar[bz])) {
                btvar[bz] = {};
            }
            var symbol =temp.symbol;
            btvar[bz][symbol]={};
            temp=bttemprmb(temp,'ETH');
            btvar[bz][symbol].asks= temp.asks;
            btvar[bz][symbol].bids= temp.bids;
            if(temp.asks.length==0||temp.bids.length==0)
            {
                console.log(symbol);
                return;
            }
            putask1to('bt',bz,'ETH',temp.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('_','');
            var result=cacule('bt',symbol,temp.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(old,temp,bz,'bt',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
    }
};

websocketbter.onclose=function () {

}