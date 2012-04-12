package com.walkin.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 public final class RegExpValidator
 {  
     /** 
      * 验证邮箱 
      * @param 待验证的字符串 
      * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean isEmail(String str)  
     {  
         String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证IP地址 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean isIP(String str)  
     {  
         String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";  
         String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";  
         return match(regex, str);  
     }  
   
     /** 
      * 验证网址Url 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsUrl(String str)  
     {  
         String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";  
         return match(regex, str);  
     }  
   
     /** 
      * 验证电话号码 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsTelephone(String str)  
     {  
         String regex = "^(\\d{3,4}-)?\\d{6,8}$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入密码条件(字符与数据同时出现) 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsPassword(String str)  
     {  
         String regex = "[A-Za-z]+[0-9]";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入密码长度 (6-18位) 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsPasswLength(String str)  
     {  
         String regex = "^\\d{6,18}$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入邮政编号 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsPostalcode(String str)  
     {  
         String regex = "^\\d{6}$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入手机号码 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsHandset(String str)  
     {  
         String regex = "^[1]+[3,5]+\\d{9}$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入身份证号 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsIDcard(String str)  
     {  
         String regex = "(^\\d{18}$)|(^\\d{15}$)";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入两位小数 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsDecimal(String str)  
     {  
         String regex = "^[0-9]+(.[0-9]{2})?$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入一年的12个月 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsMonth(String str)  
     {  
         String regex = "^(0?[[1-9]|1[0-2])$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证输入一个月的31天 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsDay(String str)  
     {  
         String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";  
         return match(regex, str);  
     }  
           
       
     /** 
      * 验证日期时间 
      * @param 待验证的字符串 
      * @return 如果是符合网址格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean isDate(String str)  
     {  
         //严格验证时间格式的(匹配[2002-01-31], [1997-04-30], [2004-01-01])不匹配([2002-01-32], [2003-02-29], [04-01-01])   
 //        String regex = "^((((19|20)(([02468][048])|([13579][26]))-02-29))|((20[0-9][0-9])|(19[0-9][0-9]))-((((0[1-9])|(1[0-2]))-((0[1-9])|(1\\d)|(2[0-8])))|((((0[13578])|(1[02]))-31)|(((01,3-9])|(1[0-2]))-(29|30)))))$";  
         //没加时间验证的YYYY-MM-DD  
 //        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";  
         //加了时间验证的YYYY-MM-DD 00:00:00  
         String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";  
         return match(regex, str);  
     }  
       
   
     /** 
      * 验证数字输入 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsNumber(String str)  
     {  
         String regex = "^[0-9]*$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证非零的正整数 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsIntNumber(String str)  
     {  
         String regex = "^\\+?[1-9][0-9]*$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证大写字母 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsUpChar(String str)  
     {  
         String regex = "^[A-Z]+$";  
         return match(regex, str);  
     }  
   
     /** 
      * 验证小写字母 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsLowChar(String str)  
     {  
         String regex = "^[a-z]+$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证验证输入字母 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsLetter(String str)  
     {  
         String regex = "^[A-Za-z]+$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证验证输入汉字 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsChinese(String str)  
     {  
         String regex = "^[\u4e00-\u9fa5],{0,}$";  
         return match(regex, str);  
     }  
       
     /** 
      * 验证验证输入字符串 
      * @param 待验证的字符串 
      * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b> 
      */  
     public static boolean IsLength(String str)  
     {  
         String regex = "^.{8,}$";  
         return match(regex, str);  
     }  
       
       
     /** 
      * @param regex 正则表达式字符串 
      * @param str 要匹配的字符串 
      * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false; 
      */  
     private static boolean match(String regex, String str)  
     {  
         Pattern pattern = Pattern.compile(regex);  
         Matcher matcher = pattern.matcher(str);  
         return matcher.matches();  
     }  
       
       
 //    3. 检查字符串重复出现的词  
 //  
 //    private void btnWord_Click(object sender, EventArgs e)  
 //    {  
 //          System.Text.RegularExpressions.MatchCollection matches = System.Text.RegularExpressions.Regex.Matches(label1.Text,   
 //  
 //            @"\b(?<word>\w+)\s+(\k<word>)\b", System.Text.RegularExpressions.RegexOptions.Compiled |            System.Text.RegularExpressions.RegexOptions.IgnoreCase);  
 //           if (matches.Count != 0)  
 //           {  
 //               foreach (System.Text.RegularExpressions.Match match in matches)  
 //               {  
 //                   string word = match.Groups["word"].Value;  
 //                   MessageBox.Show(word.ToString(),"英文单词");  
 //               }  
 //           }  
 //           else { MessageBox.Show("没有重复的单词"); }  
 //  
 //  
 //       }   
 //  
 //4. 替换字符串  
 //  
 //  private void button1_Click(object sender, EventArgs e)  
 //  {  
 //  
 //           string strResult = System.Text.RegularExpressions.Regex.Replace(textBox1.Text, @"[A-Za-z]\*?", textBox2.Text);  
 //           MessageBox.Show("替换前字符:" + "\n" + textBox1.Text + "\n" + "替换的字符:" + "\n" + textBox2.Text + "\n" +   
 //  
 //           "替换后的字符:" + "\n" + strResult,"替换");  
 //  
 //  }  
 //  
 //5. 拆分字符串  
 //  
 // private void button1_Click(object sender, EventArgs e)  
 //  {  
 //           //实例: 甲025-8343243乙0755-2228382丙029-32983298389289328932893289丁  
 //           foreach (string s in System.Text.RegularExpressions.Regex.Split(textBox1.Text,@"\d{3,4}-\d*"))  
 //           {  
 //               textBox2.Text+=s; //依次输出 "甲乙丙丁"  
 //           }  
 //  
 //   }  
     public String urlEncode(String str){
     	if(null == str || "".equalsIgnoreCase(str)){
         return str;
     	}else{
     		return URLEncoder.encode(str);
     	}
     }

     /** 
      * MD5加密算法 
      */  
     
     public final static String MD5(String s) {  
         char hexDigits[] = { '0', '1', '2', '3', '4',  
                              '5', '6', '7', '8', '9',  
                              'A', 'B', 'C', 'D', 'E', 'F'};  
         try {  
             byte[] btInput = s.getBytes();  
             MessageDigest mdInst = MessageDigest.getInstance("MD5");  
             mdInst.update(btInput);  
             byte[] md = mdInst.digest();  
             int j = md.length;  
             char str[] = new char[j * 2];  
             int k = 0;  
             for (int i = 0; i < j; i++) {  
                 byte byte0 = md[i];  
                 str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                 str[k++] = hexDigits[byte0 & 0xf];  
             }  
             return new String(str).toLowerCase();  
         }  
         catch (Exception e) {  
             e.printStackTrace();  
             return null;  
         }  
     }  
     
     /** 
      * base64加密解密算法 
      */ 
     private static char[] base64EncodeChars = new char[] {  
         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',  
         'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',  
         'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',  
         'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',  
         'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  
         'o', 'p', 'q', 'r', 's', 't', 'u', 'v',  
         'w', 'x', 'y', 'z', '0', '1', '2', '3',  
         '4', '5', '6', '7', '8', '9', '+', '/' };  
   
     private static byte[] base64DecodeChars = new byte[] {  
     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,  
     52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,  
     -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,  
     15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,  
     -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,  
     41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };  
     //编码  
     public static String encode(byte[] data) {  
         StringBuffer sb = new StringBuffer();  
         int len = data.length;  
         int i = 0;  
         int b1, b2, b3;  
         while (i < len) {  
             b1 = data[i++] & 0xff;  
             if (i == len)  
             {  
                 sb.append(base64EncodeChars[b1 >>> 2]);  
                 sb.append(base64EncodeChars[(b1 & 0x3) << 4]);  
                 sb.append("==");  
                 break;  
             }  
             b2 = data[i++] & 0xff;  
             if (i == len)  
             {  
                 sb.append(base64EncodeChars[b1 >>> 2]);  
                 sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);  
                 sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);  
                 sb.append("=");  
                 break;  
             }  
             b3 = data[i++] & 0xff;  
             sb.append(base64EncodeChars[b1 >>> 2]);  
             sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);  
             sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);  
             sb.append(base64EncodeChars[b3 & 0x3f]);  
         }  
         return sb.toString();  
     }  
     //解码  
     public static byte[] decode(String str) throws UnsupportedEncodingException {  
         StringBuffer sb = new StringBuffer();  
         byte[] data = str.getBytes("US-ASCII");  
         int len = data.length;  
         int i = 0;  
         int b1, b2, b3, b4;  
         while (i < len) {  
             /* b1 */  
             do {  
                 b1 = base64DecodeChars[data[i++]];  
             } while (i < len && b1 == -1);  
             if (b1 == -1) break;  
             /* b2 */  
             do {  
                 b2 = base64DecodeChars[data[i++]];  
             } while (i < len && b2 == -1);  
             if (b2 == -1) break;  
             sb.append((char)((b1 << 2) | ((b2 & 0x30) >>> 4)));  
             /* b3 */  
             do {  
                 b3 = data[i++];  
                 if (b3 == 61) return sb.toString().getBytes("iso8859-1");  
                 b3 = base64DecodeChars[b3];  
             } while (i < len && b3 == -1);  
             if (b3 == -1) break;  
             sb.append((char)(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));  
             /* b4 */  
             do {  
                 b4 = data[i++];  
                 if (b4 == 61) return sb.toString().getBytes("iso8859-1");  
                 b4 = base64DecodeChars[b4];  
             } while (i < len && b4 == -1);  
             if (b4 == -1) break;  
             sb.append((char)(((b3 & 0x03) << 6) | b4));  
         }  
         return sb.toString().getBytes("iso8859-1");  
     }  
   
 }  