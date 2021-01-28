/**
 * Created by Administrator on 2021-01-28.
 */
function drawtable(arr) {
    //inhtml=$("#tbodys").innerHTML;
    var inhtml="";
    $.each(arr, function (i, n) {
        inhtml+="<tr><td>"+n+"</td><td></td><td></td><td></td><td></td><td></td></tr>";
    })
    $("#tbodys")[0].innerHTML=inhtml;
}
function locateposition(type,jsdata) {
    var tmp=$.parseJSON(jsdata);
    var bizhong="";
    for (var key in tmp)
    {
        //alert(key); 	//Type, Height
        bizhong=key;
       // alert(tmp[key]);	//Coding, 100
    }
    $('#tbodys tr').each(function(){
        switch (type) {
            case 'hb':
                //遍历第一列td找到对应的币种
                if($(this).find('td').eq(0).text()==bizhong)
                {
                    $(this).find('td').eq(1)[0].innerText=eval('tmp.'+bizhong);
                    $(this).find('td').eq(1)[0].bgColor='FFFFCC';
                    setTimeout(1000);
                    $(this).find('td').eq(1)[0].bgColor='#FFFFFF';
                    return false;
                }
                break;
            case 'ok':
                if($(this).find('td').eq(0).text()==bizhong)
                {
                    $(this).find('td').eq(2)[0].innerText=eval('tmp.'+bizhong);
                    return false;
                }
                break;
            case 'bter':
                if($(this).find('td').eq(0).text()==bizhong)
                {
                    $(this).find('td').eq(3)[0].innerText=eval('tmp.'+bizhong);
                    return false;
                }
                break;
            case 'mocha':
                if($(this).find('td').eq(0).text()==bizhong)
                {
                    $(this).find('td').eq(4)[0].innerText=eval('tmp.'+bizhong);
                    return false;
                }
                break;
            case 'bian':
                if($(this).find('td').eq(0).text()==bizhong)
                {
                    $(this).find('td').eq(5)[0].innerText=eval('tmp.'+bizhong);
                    return false;
                }
                break;
            default:
                break;
        }
    });
}