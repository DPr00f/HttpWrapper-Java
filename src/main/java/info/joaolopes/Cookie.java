/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.joaolopes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Pink Donut <joaolopes at pinkdonut.net>
 */
public class Cookie {
    
    public String domain;
    public String path;
    public Date expires;
    public Boolean secure;
    public Map<String, String> cookiesValues;
    
    public Cookie(String domain, String cookieString){
        cookiesValues = new HashMap<>();
        this.domain = domain;
        this.secure = false;
        this.path = "/";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 10);
        
        this.expires = cal.getTime();
        String[] cookieData = cookieString.split("; ");
        
        for (String cookieD : cookieData) {
            String[] info = cookieD.split("=", 1);
            if(info[0].equalsIgnoreCase("path")){
                this.path = info[1];
            }else if( info[0].equalsIgnoreCase("httponly")){
                secure = true;
            }else if (info[0].equalsIgnoreCase("expires")){
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z");
                try {
                    this.expires = sdf.parse(info[1]);
                } catch (ParseException ex) {}
            }else{
                cookiesValues.put(info[0], info[1]);
            }
        }
    }
    
    public boolean hasCookie(String name){
        
        return cookiesValues.containsKey(name);
    }
    
    
    public Set<String> keys(){
        return cookiesValues.keySet();
    }
    
    public String read(String key){
        return cookiesValues.get(key);
    }
    
    
   
    
}
/**
 function Cookies(domain, path, expires, secure) {
    this.cookieDomain = ((domain && typeof domain !== 'undefined') ? domain : document.domain);
    this.cookiePath = ((path && typeof path !== 'undefined') ? path : '/');
    this.cookieExpires = ((expires && typeof expires !== 'undefined') ? expires : '');
    this.cookieSecure = ((secure && typeof secure !== 'undefined') ? true : false);

    var _cookie = this;
    return {
        domain: function (value) {
            if ((value && typeof value !== 'undefined')) {
                _cookie.cookieDomain = value
            }
            else {
                return _cookie.cookieDomain;
            }
        },
        expires: function (value) {
            if ((value && typeof value !== 'undefined')) {
                _cookie.cookieExpires = value
            }
            else {
                return _cookie.cookieExpires;
            }
        },
        hasItem: function (name) {
            return (new RegExp("(?:^|;\\s*)" + escape(name).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=")).test(document.cookie);
        },
        keys: function () {
            var keyCollection = [];
            var parentKeyCollection = document.cookie.replace(/((?:^|\s*;)[^\=]+)(?=;|$)|^\s*|\s*(?:\=[^;]*)?(?:\1|$)/g, "").split(/\s*(?:\=[^;]*)?;\s*/);

            for (var k = 0; k < parentKeyCollection.length; k++) {
                parentKeyCollection[k] = unescape(parentKeyCollection[k]);
            }

            if (parentKeyCollection && parentKeyCollection.length > 0) {
                for (var i = 0; i < parentKeyCollection.length; i++) {
                    var cookieName = parentKeyCollection[i];

                    if (cookieName) {
                        var subKeyCollection = [];
                        var keyRegEx = /([^&=\s]+)=(([^&]*)(&[^&=\s]*)*)(&|$)/g;
                        var match = keyRegEx.exec(this.read(cookieName));

                        while (match !== null) {
                            subKeyCollection.push(match[1]);
                            match = keyRegEx.exec(this.read(cookieName));
                        }

                        keyCollection.push({
                            Cookie: cookieName,
                            Keys: subKeyCollection
                        });
                    }
                }
            }

            return keyCollection;
        },
        path: function (value) {
            if ((value && typeof value !== 'undefined')) {
                _cookie.cookiePath = value
            }
            else {
                return _cookie.cookiePath;
            }
        },
        read: function (name) {
            if (!name || !this.hasItem(name)) {
                return null;
            }
            return unescape(document.cookie.replace(new RegExp("(?:^|.*;\\s*)" + escape(name).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*((?:[^;](?!;))*[^;]?).*"), "$1"));
        },
        readKey: function (name, key) {
            var keyValuePairs, keyRegEx;
            var parentCookieValue = this.read(name);
            keyValuePairs = [];

            if (!parentCookieValue || parentCookieValue === '') {
                return null;
            }

            keyRegEx = /([^&=]+)=(([^&]*)(&[^&=]*)*)(&|$)/g;
            var match = keyRegEx.exec(parentCookieValue);

            while (match !== null) {
                keyValuePairs.push([match[1], match[2]]);

                match = keyRegEx.exec(parentCookieValue);
            }

            if (keyValuePairs && keyValuePairs.length > 0) {
                for (c = 0; c < keyValuePairs.length; c++) {
                    var keyValuePair = keyValuePairs[c];
                    if (keyValuePair.length > 1) {
                        if (keyValuePair[0] == key) {
                            return unescape(keyValuePair[1].toString());
                        }
                    }
                }
            }

            return null;
        },
        remove: function (name, path) {
            if (!name || !this.hasItem(name)) {
                return;
            }
            document.cookie = escape(name) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT" + (_cookie.cookieDomain ? "; domain=" + _cookie.cookieDomain : "") + (_cookie.cookiePath ? "; path=" + _cookie.cookiePath : "");
        },
        secure: function (value) {
            if ((value && typeof value !== 'undefined')) {
                _cookie.cookieSecure = value
            }
            else {
                return _cookie.cookieSecure;
            }
        },
        write: function (name, value) {
            if (!name || /^(?:expires|max\-age|path|domain|secure)$/i.test(name)) {
                return;
            }
            var expiration = "";
            if (_cookie.cookieExpires) {
                switch (_cookie.cookieExpires.constructor) {
                    case Number:
                        expiration = _cookie.cookieExpires === Infinity ? "; expires=Tue, 19 Jan 2038 03:14:07 GMT" : "; max-age=" + _cookie.cookieExpires;
                        break;
                    case String:
                        expiration = "; expires=" + _cookie.cookieExpires;
                        break;
                    case Date:
                        expiration = "; expires=" + _cookie.cookieExpires.toGMTString();
                        break;
                }
            }
            document.cookie = escape(name) + "=" + escape(value) + expiration + (_cookie.cookieDomain ? "; domain=" + _cookie.cookieDomain : "") + (_cookie.cookiePath ? "; path=" + _cookie.cookiePath : "") + (_cookie.cookieSecure ? "; secure" : "");
        },
        writeKey: function (name, key, value) {
            var parentCookieValue = this.read(name);
            var subCookieValue = this.readKey(name, key);
            var keyValueString = key + '=' + value;
            var newCookieValue = '';

            if (!parentCookieValue || typeof parentCookieValue === 'undefined') {
                this.write(name, keyValueString);
                return;
            }

            newCookieValue = parentCookieValue.trim();

            if (subCookieValue === null || typeof subCookieValue === 'undefined') {
                newCookieValue += '&' + keyValueString;
            }
            else {
                if (newCookieValue.substr(0, key.length + 1) == (key + '=')) {
                    var totalCookieKeyLength = key.length + 1 + subCookieValue.length + 1;
                    newCookieValue = newCookieValue.substr(totalCookieKeyLength);

                    if (newCookieValue.trim() === '') {
                        newCookieValue = keyValueString;
                    }
                    else {
                        newCookieValue += '&' + keyValueString;
                    }
                }
                else {
                    var fullCookieKeyValue = '&' + key + '=' + subCookieValue;
                    if (newCookieValue.indexOf(fullCookieKeyValue) > -1) {
                        if (newCookieValue.replace(fullCookieKeyValue, '') === '') {
                            newCookieValue = keyValueString;
                        }
                        else {
                            newCookieValue = newCookieValue.replace(fullCookieKeyValue, '&' + keyValueString);
                        }
                    }
                }
            }

            this.write(name, newCookieValue);
        }
    };
}
 
 */
