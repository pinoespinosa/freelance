package com.code.injector.utils;

import java.io.UnsupportedEncodingException; 
import java.util.Hashtable; 
 
public class Generator { 
 
  public static Hashtable<String, String> code; 
 
  public static void initialize() { 
 
    code = new Hashtable<>(); 
     
    try { 
      code.put("AbstractBaseJavaComponent", 
          java.net.URLDecoder.decode( 
              "public%20abstract%20class%20AbstractBaseJavaComponent%20%7B%0A%09%0AString%20serverResource1%3B%0A%0A%09public%20abstract%20Object%20execute(Object%20parameter1)%3B%0A%09%0A%09protected%20void%20otherMethods()%20%7B%0A%09%09%2F%2F%2F%20..%20do%20something%0A%09%7D%3B%0A%09%0A%7D", 
              "UTF-8")); 
       
      code.put("Customer", 
          java.net.URLDecoder.decode( 
              "public%20class%20Customer%20%7B%0A%09String%20name%3B%0A%09Integer%20age%3B%0A%0A%09public%20Customer()%20%7B%0A%09%09super()%3B%0A%09%7D%0A%0A%09public%20Customer(String%20name%2C%20Integer%20age)%20%7B%0A%09%09super()%3B%0A%09%09this.name%20%3D%20name%3B%0A%09%09this.age%20%3D%20age%3B%0A%09%7D%0A%0A%09public%20String%20getName()%20%7B%0A%09%09return%20name%3B%0A%09%7D%0A%0A%09public%20void%20setName(String%20name)%20%7B%0A%09%09this.name%20%3D%20name%3B%0A%09%7D%0A%0A%09public%20Integer%20getAge()%20%7B%0A%09%09return%20age%3B%0A%09%7D%0A%0A%09public%20void%20setAge(Integer%20age)%20%7B%0A%09%09this.age%20%3D%20age%3B%0A%09%7D%0A%0A%7D", 
              "UTF-8")); 
 
      code.put("Vendor", 
          java.net.URLDecoder.decode( 
              "public%20class%20Vendor%20%7B%0A%0A%09String%20city%3B%0A%09Float%20sales%3B%0A%0A%09public%20Vendor(String%20city%2C%20Float%20sales)%20%7B%0A%09%09super()%3B%0A%09%09this.city%20%3D%20city%3B%0A%09%09this.sales%20%3D%20sales%3B%0A%09%7D%0A%0A%09public%20String%20getCity()%20%7B%0A%09%09return%20city%3B%0A%09%7D%0A%0A%09public%20void%20setCity(String%20city)%20%7B%0A%09%09this.city%20%3D%20city%3B%0A%09%7D%0A%0A%09public%20Float%20getSales()%20%7B%0A%09%09return%20sales%3B%0A%09%7D%0A%0A%09public%20void%20setSales(Float%20sales)%20%7B%0A%09%09this.sales%20%3D%20sales%3B%0A%09%7D%0A%0A%7D", 
              "UTF-8")); 
       
    } catch (UnsupportedEncodingException e) { 
      e.printStackTrace(); 
    } 
 
  } 
 
  public static String getCode(String clase) { 
 
    if (code == null) 
      initialize(); 
 
    return code.get(clase); 
 
  } 
 
} 