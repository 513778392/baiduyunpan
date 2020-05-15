package data;

import android.os.Bundle;
import android.os.Message;

import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */
public class JavaScript {
   public static String message = "javascript:(function(){function $(selector) {if(selector[0]==='#') return document.getElementById(selector.substring(1));else return Array.prototype.slice.call(document.querySelectorAll(selector));}$.urlParams = function(params) {var query = '';for(var name in params) {if(query.length!==0) query += '&';var value = params[name];if(value==null) continue;if(value==='') {query += encodeURIComponent(name);} else {if(typeof value !== 'string') value = JSON.stringify(value);query += encodeURIComponent(name) + '=' + encodeURIComponent(value);}}return query;};$.ajax = function(method, url, params, success, fail, always) {if(params!=null && typeof params !== 'string') {params = $.urlParams(params);}var req = new XMLHttpRequest();var isGET = method.toUpperCase() === 'GET';if(isGET && params) url += (url.indexOf('?')===-1 ? '?' : '&') + params;req.open(method, url, true);req.onreadystatechange = function(){if (this.readyState == 4){var responseJson = null, responseText = this.responseText;try{responseJson = JSON.parse(responseText);if(typeof responseJson !== 'object') responseJson = null;}catch(err){}if(this.status == 200 && responseJson != null) {success && success(responseJson, responseText);} else {fail && fail(this.status, responseJson, responseText);}always && always(this.status, responseJson, responseText);}};req.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");req.send(isGET ? null : params);};$.get = function(url, params, success, fail, always) {$.ajax('GET', url, params, success, fail, always);};$.post = function(url, params, success, fail, always) {$.ajax('POST', url, params, success, fail, always);};function setCookie(name, value, seconds, path, domain, secure) {if(value == '' || seconds < 0) {value = '';seconds = -1;}if(seconds) {var expires = new Date();expires.setTime(expires.getTime() + seconds * 1000);}if(path==null) path='/';document.cookie = encodeURIComponent(name) + '=' + encodeURIComponent(value)+ (expires ? '; expires=' + expires.toGMTString() : '')+ (path ? '; path=' + path : '')+ (domain ? '; domain=' + domain : '')+ (secure ? '; secure' : '');}function delCookie(name, path, domain, secure) {setCookie(name, '', -1, path, domain, secure);}function getCookie(name) {name = encodeURIComponent(name) + '=';var cookie_start = document.cookie.indexOf(name);if(cookie_start == -1) return '';var cookie_end = document.cookie.indexOf(\";\", cookie_start);if(cookie_end == -1) cookie_end = document.cookie.length;return decodeURIComponent(document.cookie.substring(cookie_start + name.length, cookie_end));}function Request(action) {this.action = action;this.respon = function(data, msg, code) {var res = {action: this.action,error: code,msg: msg,data: data,};var jsonString = JSON.stringify(res);if(window.prompt) window.prompt(jsonString);if(console&&console.log) console.log(jsonString);};this.success = function(data) {if(data==null) data=null;this.respon(data, '', 0);};this.error = function(msg, code) {if(msg==null) msg = '';if(code==null) code = 1;this.respon(null, msg, code);};this.text = function(text) {if(window.prompt) window.prompt(text);if(console&&console.log) console.log(text);};}var bdUtils = function(){var userId, bdstoken;return {$: $,share_yunData: {},bdstoken: function() {if(!bdstoken) bdstoken = window.yunData.MYBDSTOKEN;return bdstoken;},userId: function() {if(!userId) userId = window.yunData.MYUK;return userId;},randomPwd: function(length) {if(length==null) length = 4;var chars = 'abcdefghijkmnpqrstuvwxyz23456789';var randomChars = \"\";for(var n=0; n<length; n++) {var i = Math.floor(Math.random() * chars.length);randomChars += chars.charAt(i);}return randomChars;},shareId: function() {return this.share_yunData.SHARE_ID;},shareUserId: function() {return this.share_yunData.SHARE_UK;},commonParams: function() {return 'channel=chunlei&web=1&app_id=250528&clienttype=0&t=' + new Date().getTime() + '&bdstoken=' + bdUtils.bdstoken();},magnetTaskStatusText: function(status) {if(status=='0') return '下载成功';if(status=='4') return '下载失败';if(status=='1') return '0%(0B/s)';return '下载中';},};}();function bd_test(){var req = new Request('test');req.success('测试');}function bd_listDir(path) {var req = new Request('listDir');var url = '/api/list?folder=1&order=name&desc=0&showempty=0&page=0&num=500&' + bdUtils.commonParams();$.get(url, {dir: path}, function success(data){if(data.errno) {req.error();} else {var list = [];for(var i=0; i<data.list.length; i++) {var path = data.list[i].path;list.push({name: path.split('/').pop(),path: path,});}req.success(list);}});}function bd_listFile(path) {var req = new Request('listFile');var url = '/api/list?order=time&desc=0&showempty=0page=1&num=500&' + bdUtils.commonParams();$.get(url, {dir: path}, function success(data){if(data.errno) {req.error();} else {var list = [];for(var i=0; i<data.list.length; i++) {var item = data.list[i];list.push({fs_id: item.fs_id,isdir: item.isdir==1,md5: item.md5,name: item.server_filename,mtime: item.server_mtime,path: item.path,size: item.size,thumb: item.thumbs&&item.thumbs.icon ? item.thumbs.icon : '',});}req.success(list);}});}function bd_rename(filePath, newName) {var req = new Request('rename');var url = '/api/filemanager?opera=rename&async=1&onnest=fail&' + bdUtils.commonParams();$.post(url, {filelist: [{path: filePath, newname: newName}],}, function success(data){if(data.errno) {req.error();} else {req.success();}});}function bd_move(files, newDirPath) {var req = new Request('move');var url = '/api/filemanager?opera=move&async=1&onnest=fail&' + bdUtils.commonParams();var filelist = [];for(var i=0; i<files.length; i++) {var oldFilePath = files[i];var fileName = oldFilePath.split('/').pop();filelist.push({path: oldFilePath, dest: newDirPath, newname: fileName});}$.post(url, {filelist: filelist,}, function success(data){if(data.errno) {req.error();} else {req.success();}});}function bd_delete(files) {var req = new Request('delete');var url = '/api/filemanager?opera=delete&async=1&onnest=fail&' + bdUtils.commonParams();$.post(url, {filelist: files,}, function success(data){if(data.errno) {req.error();} else {req.success();}});}function bd_share(fs_id_list, addPwd) {if(addPwd==null) addPwd = false;var req = new Request('share');var url = '/share/set?' + bdUtils.commonParams();var pwd = '';var postData = {fid_list: fs_id_list,schannel: addPwd ? 4 : 0,channel_list: [],period: 0,};if(addPwd) {pwd = bdUtils.randomPwd();postData['pwd'] = pwd;}$.post(url, postData, function success(data){if(data.errno) {req.error();} else {req.success({shareId: data.shareid,shareUrl: data.shorturl?data.shorturl:data.link,pwd: pwd,});}});}function bd_magnetList(magnet) {var req = new Request('magnetList');var url = '/rest/2.0/services/cloud_dl?' + bdUtils.commonParams();$.post(url, {method: 'query_magnetinfo',source_url: magnet,save_path: '/',type: 4,}, function success(data){if(data.magnet_info==null) {req.error();} else {var list = [];for(var i=0; i<data.magnet_info.length; i++) {var item = data.magnet_info[i];if(/(^|\\/)_____padding_file_\\d+_如果您看到此文件，请升级到BitComet\\(比特彗星\\)0\\.85或以上版本____$/.test(item.file_name)) continue;list.push({index: i+1,name: item.file_name,size: item.size,});}req.success(list);}});}function bd_magnetSave(magnet, selectedIndexes, savePath) {var req = new Request('magnetSave');var url = '/rest/2.0/services/cloud_dl?' + bdUtils.commonParams();$.post(url, {method: 'add_task',save_path: savePath,selected_idx: selectedIndexes.join(','),task_from: 1,source_url: magnet,type: 4,}, function success(data){if(data.error_code || data.task_id==null) {req.error();} else {req.success({taskId: data.task_id});}});}function bd_magnetTask(taskIds) {var req = new Request('magnetTask');var url = '/rest/2.0/services/cloud_dl?' + bdUtils.commonParams();$.get(url, {method: 'query_task',task_ids: taskIds.join(','),op_type: 1,}, function success(data){if(data.error_code || data.task_info==null) {req.error();} else {var list = [];for(var taskId in data.task_info) {var item = data.task_info[taskId];var fileList = [];if(item.file_list) {for(var i=0; i<item.file_list.length; i++) {var fileItem = item.file_list[i];fileList.push({name: fileItem.file_name,size: fileItem.file_size,});}}list.push({taskId: taskId,taskName: item.task_name,size: item.file_size,fileList: fileList,savePath: item.save_path,sourceUrl: item.source_url,status: item.status,statusText: bdUtils.magnetTaskStatusText(item.status),createTime: item.create_time,});}req.success(list);}});}function bd_magnetTaskList(page) {var req = new Request('magnetTaskList');if(page==null) page=1;var limit=10, start=(page-1)*limit;var url = '/rest/2.0/services/cloud_dl?' + bdUtils.commonParams() + '&need_task_info=1&status=255';$.get(url, {method: 'list_task',start: start,limit: limit,}, function success(data){if(data.task_info==null) {req.error();} else {var list = [];for(var i=0; i<data.task_info.length; i++) {var item = data.task_info[i];list.push({taskId: item.task_id,taskName: item.task_name,sourceUrl: item.source_url,savePath: item.save_path,status: item.status,statusText: bdUtils.magnetTaskStatusText(item.status),createTime: item.create_time,});}req.success({list: list,page: page,totalPage: Math.ceil(data.total / limit),});}});}function bd_enterShare(shareUrl, pwd) {var req = new Request('enterShare');var urlPath = shareUrl.replace(/^((\\w+\\:)?\\/\\/)?[^\\/]+/, '');if(urlPath[0]!=='/') urlPath = '/' + urlPath;shareUrl = 'https://pan.baidu.com' + urlPath;var runEnterShare = function() {var hasTested = false;bdUtils.share_yunData = {};var handler = function (status, json, html){var match, SHARE_ID, SHARE_UK, SHARE_USER_NAME, FILEINFO;var singleFileMatcher = function() {match = html.match(/\\syunData\\.setData\\((\\{.*\\})\\)\\;\\s*\\n/);if(match) {var yunData = eval('(' + match[1] + ')');FILEINFO = yunData.file_list.list;SHARE_ID = yunData.shareid;SHARE_UK = yunData.uk;SHARE_USER_NAME = yunData.linkusername;return true;} else {return false;}};var multiFileMatcher = function() {match = html.match(/\\syunData\\.FILEINFO\\s*=\\s*(\\[.*\\]);\\s*\\n/);if(match) {FILEINFO = eval(match[1]);} else {if(hasTested) {req.error('分享地址不存在');return false;}hasTested = true;$.get(shareUrl, null, null, null, handler);return false;}match = html.match(/\\syunData.SHARE_ID\\s*=\\s*(.*);\\s*\\n/);if(match) {SHARE_ID = eval(match[1]);} else {req.error();return false;}match = html.match(/\\syunData.SHARE_UK\\s*=\\s*(.*);\\s*\\n/);if(match) {SHARE_UK = eval(match[1]);} else {req.error();return false;}match = html.match(/\\syunData.SHARE_USER_NAME\\s*=\\s*(.*);\\s*\\n/);if(match) {SHARE_USER_NAME = eval(match[1]);} else {req.error();return false;}return true;};if(!singleFileMatcher() && !multiFileMatcher()) return;bdUtils.share_yunData = {SHARE_ID: SHARE_ID,SHARE_UK: SHARE_UK,SHARE_USER_NAME: SHARE_USER_NAME,FILEINFO: FILEINFO,};var list = [];for(var i=0; i<bdUtils.share_yunData.FILEINFO.length; i++) {var item = bdUtils.share_yunData.FILEINFO[i];if(item.isdelete!=null && item.isdelete!='0') continue;list.push({fs_id: item.fs_id,isdir: item.isdir==1,md5: item.md5,name: item.server_filename,mtime: item.server_mtime,path: item.path,size: item.size,thumb: item.thumbs&&item.thumbs.icon ? item.thumbs.icon : '',});}req.success(list);};$.get(shareUrl, null, null, null, handler);};if(pwd) {var surl = '';var matches = /\\?surl=(\\w+)/i.exec(shareUrl);if(matches) {surl = matches[1];} else {matches = /\\/s\\/\\d(\\w+)/i.exec(shareUrl);if(matches) surl = matches[1];}if(!surl) {req.error('无效的分享地址');return;}var verifyUrl ='/share/verify?' + bdUtils.commonParams() + '&surl='+surl;$.post(verifyUrl, {pwd: pwd,vcode: '',vcode_str: '',}, function success(data){if(data.errno) {req.error('密码错误');} else {runEnterShare();}});} else {runEnterShare();}}function bd_enterShare_listFile(path) {var req = new Request('enterShare_listFile');var shareId = bdUtils.shareId();var shareUserId = bdUtils.shareUserId();var url = '/share/list?order=other&desc=1&showempty=0&' + bdUtils.commonParams() + '&page=1&num=500&shareid='+shareId+'&uk='+shareUserId;$.get(url, {dir: path,}, function success(data){if(data.errno) {req.error();} else {var list = [];for(var i=0; i<data.list.length; i++) {var item = data.list[i];list.push({fs_id: item.fs_id,isdir: item.isdir==1,md5: item.md5,name: item.server_filename,mtime: item.server_mtime,path: item.path,size: item.size,thumb: item.thumbs&&item.thumbs.icon ? item.thumbs.icon : '',});}req.success(list);}});}function bd_enterShare_transfer(files, savePath) {var req = new Request('enterShare_transfer');var shareId = bdUtils.shareId();var shareUserId = bdUtils.shareUserId();var userId = bdUtils.userId();if(userId == shareUserId) {req.error('自己分享的文件，不可转存');return;}var url = '/share/transfer?ondup=newcopy&async=1&'  + bdUtils.commonParams() + '&shareid='+shareId+'&from='+shareUserId;$.post(url, {filelist: files,path: savePath,}, function success(data){if(data===null) req.error('网络错误，请稍后重试');else if(data.errno == 0) req.success();else if(data.errno == -6) req.error('请登录');else if(data.errno == 111) req.error('当前有一个保存任务正在进行，请稍后保存');else if(data.errno == 12) {for(var i=0, infoList = data.info; i<infoList.length; i++) {var errno = infoList[i].errno;if(errno != 0) {if(errno == -33) req.error('文件过多无法转存，减少点文件试试吧');else if(errno == 120) req.error('所选文件数量超出1000个上限，无法保存');else if(errno == 130) req.error('所选文件数量超出3000个上限，无法保存');return;}}req.error('文件过多无法转存，减少点文件试试吧');}else if(data.errno == -33) req.error('文件过多无法转存，减少点文件试试吧');else if(data.errno == 120) req.error('所选文件数量超出1000个上限，无法保存');else if(data.errno == 130) req.error('所选文件数量超出3000个上限，无法保存');else req.error('保存失败，请稍后重试');}, function fail(){req.error('数据错误，请稍后重试');});}window.JSBridge = {bdUtils: bdUtils,test: bd_test,listDir: bd_listDir,listFile: bd_listFile,rename: bd_rename,move: bd_move,'delete': bd_delete,share: bd_share,magnetList: bd_magnetList,magnetSave: bd_magnetSave,magnetTask: bd_magnetTask,magnetTaskList: bd_magnetTaskList,enterShare: bd_enterShare,enterShare_transfer: bd_enterShare_transfer,enterShare_listFile: bd_enterShare_listFile,};}());";


    public static String getYunpandata(String mulu,int page){
        return  "http://pan.baidu.com/api/list?dir="+mulu+"&bdstoken=f87919bf8ced432a9ded22b70e2a5146&order=time&desc=1&clienttype=0&showempty=0&web=1&page="+page+"&channel=chunlei&web=1&app_id=250528";
    }


    public static String path = "https://pan.baidu.com/";
    public static String url = "http://10.0.0.110:8071/baidujs.php";

    public static String JIAOCHENG = "https://jingyan.baidu.com/article/e8cdb32b1eb0d337042bad60.html?qq-pf-to=pcqq.c2c";
    //移动文件
    public static String getMove(String jiuWenjian,String xinMuLu){
        return "javascript:JSBridge.move("+jiuWenjian+",\""+xinMuLu+"\")";
    }
    //文件重命名
    public static String getRename(String jiuWenjian,String xinMuLu){
        return "javascript:JSBridge.rename(\'"+jiuWenjian+"\',\""+xinMuLu+"\")";
    }
    //获取目录列表(仅目录)
    public static String getListDir(String type){
        return "javascript:JSBridge.listDir(\'"+type+"\')";
    }
    //获取文件列表(目录+文件)
    public static String getListFile(String type){
        return "javascript:JSBridge.listFile(\'"+type+"\')";
    }
    //分享文件
    public static String getFenxiang(String fs_id,boolean isJiami){
        return "javascript:JSBridge.share("+fs_id+","+isJiami+")";
    }

    //进入分享文件
    public static String getFenxiangWenjian(String lianjie,String mima){
        return "javascript:JSBridge.enterShare(\""+lianjie+"\",\""+mima+"\")";
    }
    //获取分享文件列表
    public static String getHuoquFenxiangWenjian(String lianjie){
        return "javascript:JSBridge.enterShare_listFile(\""+lianjie+"\")";
    }
    //删除文件
    public static String getShanchuWenjian(String wenjian){
        return "javascript:JSBridge.delete("+wenjian+")";
    }

    //分享转存文件
    public static String getZhuancunWenjian(String lianjie,String mulu){
        return "javascript:JSBridge.enterShare_transfer("+lianjie+",\'"+mulu+"\')";
    }
    //获取磁力链接的内部文件列表
    public static String getCiLiWenjianLiebiao(String cili){
        return   "javascript:JSBridge.magnetList(\'"+cili+"\')";
    }
    //保存磁力链接到目录
    public static String getBaocunCiliLianjie(String cili,String mulu){
        return   "javascript:JSBridge.magnetSave(\'"+cili+"\',\'"+mulu+"\')";
    }
    //获取离线下载任务的进度
    public static String getXiazaiJindu(String taskId){
        return   "javascript:JSBridge.magnetTask("+taskId+")";
    }
    //获取离线下载任务列表
    public static String getXiazaiLiebiao(String taskId){
        return   "javascript:JSBridge.magnetTaskList("+taskId+")";
    }

    public static String getImage(String path){
        return "http://pcs.baidu.com/rest/2.0/pcs/thumbnail?method=generate&path="+path+"&quality=80&size=c720_u1280&app_id=250528&devuid=875497020550968";
    }



    //Json转list
    public static <T> List<T> getObjectList(String jsonString, Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**获取javascript数据方法*/
    private void getWenBen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Message m = new Message();
                    Bundle b = new Bundle();
                    b.putString("b", JavaScript.readTxtFile(JavaScript.url,"utf-8"));
                    m.setData(b);
                    m.what = 2;
                   // handler.sendMessage(m);
                }catch (Exception e){

                }
            }
        }).start();
    }
    //获取javascript 文本内容
    public static String readTxtFile(String urlStr, String encoding)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            // 创建一个URL对象
            URL url = new URL(urlStr);
            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
            //设置请求方式
            urlConn.setRequestMethod("GET");
            //设置请求超时时间
            urlConn.setConnectTimeout(5000);
            //设置读取超时时间
            urlConn.setReadTimeout(5000);
            // 使用IO流读取数据
            buffer = new BufferedReader(new InputStreamReader(urlConn
                    .getInputStream(), encoding));

            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public static int getTubiao(String fileName){
        int icon = 0;
        String houzui = fileName.substring(fileName.lastIndexOf(".")+1);
        LogUtil.v(houzui);
        if(houzui.equalsIgnoreCase("apk")){
            icon = R.mipmap.icon_list_apk;
        }else if(houzui.equalsIgnoreCase("mp3")){
            icon = R.mipmap.icon_list_audiofile;
        }else if(houzui.equalsIgnoreCase("zip")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("7z")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("7-zip")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("rar")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("doc")){
            icon = R.mipmap.icon_list_doc;
        }else if(houzui.equalsIgnoreCase("docx")){
            icon = R.mipmap.icon_list_doc;
        }else if(houzui.equalsIgnoreCase("xls")){
            icon = R.mipmap.icon_list_excel;
        }else if(houzui.equalsIgnoreCase("pdf")){
            icon = R.mipmap.icon_list_pdf;
        }else if(houzui.equalsIgnoreCase("ppt")){
            icon = R.mipmap.icon_list_ppt;
        }else if(houzui.equalsIgnoreCase("txt")){
            icon = R.mipmap.icon_list_txtfile;
        }else if(houzui.equalsIgnoreCase("vcf")){
            icon = R.mipmap.icon_list_vcard;
        }else if(houzui.equalsIgnoreCase("vsd")){
            icon = R.mipmap.icon_list_visio;
        }else if(houzui.equalsIgnoreCase("mp4")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("mkv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("wmv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("3GP")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("flv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("AVI")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("wma")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("rmvb")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("flv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("rm")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("flash")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("mid")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("html")){
            icon = R.mipmap.icon_list_html;
        }else if(houzui.equalsIgnoreCase("exe")){
            icon = R.mipmap.exe;
        }else if(houzui.equalsIgnoreCase("torrent")){
            icon = R.mipmap.bt;
        }else {
            icon = R.mipmap.icon_list_unknown;
        }

        return icon;
    }
    public static int getTubiao1(String fileName){
        int icon = 0;
        String houzui = fileName.substring(fileName.lastIndexOf(".")+1);
        if(houzui.equalsIgnoreCase("apk")){
            icon = R.mipmap.icon_list_apk;
        }else if(houzui.equalsIgnoreCase("mp3")){
            icon = R.mipmap.icon_list_audiofile;
        }else if(houzui.equalsIgnoreCase("zip")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("7z")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("7-zip")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("rar")){
            icon = R.mipmap.icon_list_compressfile;
        }else if(houzui.equalsIgnoreCase("doc")){
            icon = R.mipmap.icon_list_doc;
        }else if(houzui.equalsIgnoreCase("docx")){
            icon = R.mipmap.icon_list_doc;
        }else if(houzui.equalsIgnoreCase("xls")){
            icon = R.mipmap.icon_list_excel;
        }else if(houzui.equalsIgnoreCase("pdf")){
            icon = R.mipmap.icon_list_pdf;
        }else if(houzui.equalsIgnoreCase("ppt")){
            icon = R.mipmap.icon_list_ppt;
        }else if(houzui.equalsIgnoreCase("txt")){
            icon = R.mipmap.icon_list_txtfile;
        }else if(houzui.equalsIgnoreCase("vcf")){
            icon = R.mipmap.icon_list_vcard;
        }else if(houzui.equalsIgnoreCase("vsd")){
            icon = R.mipmap.icon_list_visio;
        }else if(houzui.equalsIgnoreCase("flv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("mp4")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("mkv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("avi")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("wmv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("3GP")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("flv")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("AVI")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("wma")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("rmvb")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("rm")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("flash")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("mid")){
            icon = R.mipmap.icon_list_videofile;
        }else if(houzui.equalsIgnoreCase("html")){
            icon = R.mipmap.icon_list_html;
        }else if(houzui.equalsIgnoreCase("exe")){
            icon = R.mipmap.exe;
        }else if(houzui.equalsIgnoreCase("torrent")){
            icon = R.mipmap.bt;
        }else {
            icon = R.mipmap.wenjj;
        }

        return icon;
    }
}
