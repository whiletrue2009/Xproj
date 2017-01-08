package com.whiletrue.xproj.xstock.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public final class XDataJsParser {

	private XDataJsParser(){
		
		
	}
	public static String parseJS(String jsData,String jsKey) {
		 
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
//		Object result = cx.evaluateString(scope, jsData+"var tt = JSON.stringify(oLhbxxDatas._datas);tt",
//				"t_name", 1, null);
		Object result = cx.evaluateString(scope, jsData+jsKey,
				"t_name", 1, null);
		System.out.println(result);
		// System.out.println(scope.get("tt", scope));
//		System.out.println(NativeJSON.stringify(cx, scope, result, null, null));
		Context.exit();
		
		return result.toString();
	}
	
	
	
	public static void main(String[] args) {
		Context ctx = Context.enter();
        Scriptable scope = ctx.initStandardObjects();
        
        scope.put("x",scope, new Integer(20));
        scope.put("y",scope, new Integer(30)); 
        try
        {
          ctx.evaluateString(scope,"result = x == y","",1,null);
          System.out.println(scope.get("result", scope));
        }
        finally
        {
            Context.exit();
        }
	}
}
