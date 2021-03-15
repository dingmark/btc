/**
 * Created by Administrator on 2021-01-26.
 */
var btvar={};
if ('WebSocket' in window) {
    websocketbter = new WebSocket("ws://localhost:8080/test/bt");
}
else
{
    alert('Not support websocket')
}
websocketbter.onmessage=function(event)
{
    var temp=JSON.parse(event.data.replace('\\',''));
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
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
            break;
    }
};

websocketbter.onclose=function () {

}