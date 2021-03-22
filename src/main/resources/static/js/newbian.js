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
            break;
    }

};

websocketbian.onclose=function () {

};