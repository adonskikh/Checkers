/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Алина
 */
public final class MD5
{
    public MD5(){ }
    
    public  String getHash(String string)   
    {
        MessageDigest md5;
        StringBuilder hexString = new StringBuilder();
        
        try
        {
            md5 = MessageDigest.getInstance("md5");
            
            md5.reset();
            md5.update(string.getBytes());
            
            byte messageDigest[] = md5.digest();
            
            for(int i = 0; i < messageDigest.length; i++)
            {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
        }catch (NoSuchAlgorithmException e)
        {
            return  e.toString();
        }
        
        return  hexString.toString();
    }
}
