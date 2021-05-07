/**
 * Created by Administrator on 2021-05-06.
 */
$(document).ready(function(){
    $("#table_content").on('click','button',function (e) {
        // alert(e);
        // $(this)[0].attributes[7].textContent='aaaa';
        $("[data-toggle='popover']").popover();
        // f1(this,f2);
    });
    $("#selectbuy").on('change',function (e) {
        buyselect(this.value);
        sellselect($("#selectsell").val());
        txtchange($("#bzselect").val());
    });
    $("#selectsell").on('change',function (e) {
        buyselect($("#selectbuy").val());
        sellselect(this.value);
        txtchange($("#bzselect").val());
    });

    $("#bzselect").on('change',function (e) {

       // buyselect($("#selectbuy").val());
        //sellselect($("#selectsell").val());
        txtchange(this.value);
    });
});
function f1(aa,callback) {
    // $(aa)[0].attributes[7].textContent='aaaa';
    callback();
}
function f2() {
    $("[data-toggle='popover']").popover();
}
function buyselect(buy) {
    switch (buy)
    {
        case'ok':
            $('tr:visible').find(".buyhb").parent().hide();
            $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            // $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            //$("tr").find(".buyok").parent().show();
            break;
        case'hb':
            //$('tr:visible').find(".buyhb").parent.hide();
            $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            break;
        case 'bn':
            $('tr:visible').find(".buyhb").parent().hide();
            // $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            break;
        case 'bt':
            $('tr:visible').find(".buyhb").parent().hide();
            $('tr:visible').find(".buybn").parent().hide();
            //$('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            break;
        case 'bs':
            $('tr:visible').find(".buyhb").parent().hide();
            $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            //$('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            break;
        case 'kb':
            $('tr:visible').find(".buyhb").parent().hide();
            $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            //$('tr:visible').find(".buykb").parent().hide();
            break;
        case 'zb':
            $('tr:visible').find(".buyhb").parent().hide();
            $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            $('tr:visible').find(".buymc").parent().hide();
            //$('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            break;
        case 'mc':
            $('tr:visible').find(".buyhb").parent().hide();
            $('tr:visible').find(".buybn").parent().hide();
            $('tr:visible').find(".buybt").parent().hide();
            $('tr:visible').find(".buyok").parent().hide();
            // $('tr:visible').find(".buymc").parent().hide();
            $('tr:visible').find(".buyzb").parent().hide();
            $('tr:visible').find(".buybs").parent().hide();
            $('tr:visible').find(".buykb").parent().hide();
            break;
        case 'all':
            $('.warning').show();
            break;
    }
}
function sellselect(sell) {
    switch (sell)
    {
        case'ok':
            // $(".warning").hide();
            //$(".buyok").parent().show();
            $('tr:visible').find(".sellhb").parent().hide();
            $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            // $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            //$("tr").find(".sellok").parent().show();
            break;
        case'hb':
            //$('tr:visible').find(".sellhb").parent.hide();
            $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'bn':
            $('tr:visible').find(".sellhb").parent().hide();
            // $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'bt':
            $('tr:visible').find(".sellhb").parent().hide();
            $('tr:visible').find(".sellbn").parent().hide();
            //$('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'bs':
            $('tr:visible').find(".sellhb").parent().hide();
            $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            //$('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'kb':
            $('tr:visible').find(".sellhb").parent().hide();
            $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            //$('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'zb':
            $('tr:visible').find(".sellhb").parent().hide();
            $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            $('tr:visible').find(".sellmc").parent().hide();
            //$('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'mc':
            $('tr:visible').find(".sellhb").parent().hide();
            $('tr:visible').find(".sellbn").parent().hide();
            $('tr:visible').find(".sellbt").parent().hide();
            $('tr:visible').find(".sellok").parent().hide();
            // $('tr:visible').find(".sellmc").parent().hide();
            $('tr:visible').find(".sellzb").parent().hide();
            $('tr:visible').find(".sellbs").parent().hide();
            $('tr:visible').find(".sellkb").parent().hide();
            break;
        case 'all':
            $('tr:visible').show();
            break;
    }
    
}
function txtchange(txt) {
    if($.trim(txt)!='')
    {
        $('tr:visible').find(".btn-warning").not("."+this.value)
            .parent().parent().hide();
    }
    else
    {
        $('tr:visible').show();
    }
}