/**
 * Created by Administrator on 2021-03-15.
 */
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
    switch (trade) {
        case 'hb':
            //var i=asksvar[bz][trade].length;
            f(trade, bz, base, ask1);
            //asksvar[bz][trade][i]=ask1;
            break;
        case 'bn':
            //asksvar[bz][1]=ask1;
            f(trade, bz, base, ask1);
            break;
        case 'bt':
            // asksvar[bz][2]=ask1;
            f(trade, bz, base, ask1);
            break;
        case 'kb':
            f(trade, bz, base, ask1);
            // asksvar[bz][3]=ask1;
            break;
        case'mc':
            //asksvar[bz][4]=ask1;
            f(trade, bz, base, ask1);
            break;
        case'bs':
            //asksvar[bz][5]=ask1;
            f(trade, bz, base, ask1);
            break;
        case'ok':
            //asksvar[bz][6]=ask1;
            f(trade, bz, base, ask1);
            break;
        case 'zb':
            // asksvar[bz][7]=ask1;
            f(trade, bz, base, ask1);
            break
    }
    function f(trade, bz, base, ask1) {
        if ('undefined' == typeof (asksvar[bz][trade][base])) {
            asksvar[bz][trade][base] = {};
        }
        asksvar[bz][trade][base] = ask1;
    }
}
    //找到币种的最大值。并且返回交易对和最大值
    function findmax (bz)
    {
        var maxvalue={"symbol":"symbol","max":0};
        var temp=asksvar[bz];
        for (var o in temp)
        {
            for(var p in temp[o])
            {
                if(maxvalue.max<temp[o][p])
                {
                    maxvalue.symbol=p;
                    maxvalue.max=temp[o][p];
                }
            }
        }
        return maxvalue;
    }
