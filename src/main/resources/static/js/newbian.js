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
        case'btc':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('btc'));
            if('undefined'==typeof (bnvar[bz])) {
                bnvar[bz] = {};
            }
            var symbol =temp.symbol;
            bnvar[bz][symbol]={};
            temp=bntemprmb(temp,'btc');
            bnvar[bz][symbol].asks= temp.asks;
            bnvar[bz][symbol].bids= temp.bids;
            break;
        case 'sdt':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('usdt'));
            if('undefined'==typeof (bnvar[bz])) {
                bnvar[bz] = {};
            }
            var symbol =temp.symbol;
            bnvar[bz][symbol]={};
            temp=bntemprmb(temp,'usdt');
            bnvar[bz][symbol].asks= temp.asks;
            bnvar[bz][symbol].bids= temp.bids;
            break;
        case 'eth':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('eth'));
            if('undefined'==typeof (bnvar[bz])) {
                bnvar[bz] = {};
            }
            var symbol =temp.symbol;
            bnvar[bz][symbol]={};
            temp=bntemprmb(temp,'eth');
            bnvar[bz][symbol].asks= temp.asks;
            bnvar[bz][symbol].bids= temp.bids;
            break;
    }

};

websocketbian.onclose=function () {

};