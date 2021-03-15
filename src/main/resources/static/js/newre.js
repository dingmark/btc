/**
 * Created by Administrator on 2021-03-15.
 */
var revar={};
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
        case 'btc':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbbtc;
            }
            break;
        case 'usdt':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'eth':
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
        case 'btc':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*bnbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*bnbtc;
            }
            break;
        case 'usdt':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*hbrmb;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*hbrmb;
            }
            break;
        case 'eth':
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
        case 'btc':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*zbbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*zbbtc;
            }
            break;
        case 'usdt':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][0]=temp.asks[i][0]*zbqc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][0]=temp.bids[i][0]*zbqc;
            }
            break;
        case 'qc':

            break;
    }

    return temp;
}

function bstemprmb(temp,zrmb) {
    switch (zrmb)
    {
        case 'btc':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][1]=temp.asks[i][1]*bsbtc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][1]=temp.bids[i][1]*bsbtc;
            }
            break;
        case 'usdt':
            for(i=0;i<temp.asks.length;i++)
            {
                temp.asks[i][1]=temp.asks[i][1]*bscnc;
            }
            for(i=0;i<temp.bids.length;i++)
            {
                temp.bids[i][1]=temp.bids[i][1]*bscnc;
            }
            break;
        case 'cnc':

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