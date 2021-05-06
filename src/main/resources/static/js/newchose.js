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
        switch (this.value)
        {
            case'ok':
                $(".warning").hide();
                //$(".buyok").parent().show();
                $("tr").find(".buyok").parent().show();
                break;
            case'hb':
                $(".warning").hide();
                //$(".buyhb").parent().show();
                $("tr").find(".buyhb").parent().show();
                break;
            case 'all':
                $(".warning").show();
                break;
        }
    });
    $("#selectsell").on('change',function (e) {
        switch (this.value)
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
            case 'ok':
                $('tr:visible').find(".sellhb").parent().hide();
                $('tr:visible').find(".sellbn").parent().hide();
                $('tr:visible').find(".sellbt").parent().hide();
                //$('tr:visible').find(".sellok").parent().hide();
                $('tr:visible').find(".sellmc").parent().hide();
                $('tr:visible').find(".sellzb").parent().hide();
                $('tr:visible').find(".sellbs").parent().hide();
                $('tr:visible').find(".sellkb").parent().hide();
                break;
            case 'all':
                $(".warning").show();
                break;
        }
    });

    $("#bz").on('change',function (e) {
        //alert(1111);
        $('tr :visible').find(".btn-warning").not("."+this.value)
            .parent().parent().hide();
        //$('tr:visible').find("."+this.value).parent().show();
       // $("tr").find(".sellhb").parent().show();
    });
});
function f1(aa,callback) {
    // $(aa)[0].attributes[7].textContent='aaaa';
    callback();
}
function f2() {
    $("[data-toggle='popover']").popover();
}