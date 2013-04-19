package com.destinym.cplusplustestking;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

public class PageBkg implements Serializable
{
public static int height = 0;
public static int width = 0;
public int id = -1;
public String name = "";

// ERROR //
private static List a(InputStream paramInputStream)
{
	return null;
  //  
}

public static PageBkg getBkg(Context paramContext, int paramInt)
{
  Object localObject1 = null;
  try
  {
    AssetManager localAssetManager = paramContext.getAssets();
    Iterator localIterator = getBkgs(localAssetManager.open("bkg/bkg.xml")).iterator();
    Object localObject3;
	IOException localObject2;
	try
    {
      while (true)
      {
        if (!localIterator.hasNext())
          return (PageBkg) localObject1;
        PageBkg localPageBkg = (PageBkg)localIterator.next();
        HashMap localHashMap = new HashMap();
        localHashMap.put("name", localPageBkg.name);
        InputStream localInputStream = localAssetManager.open("bkg/" + localPageBkg.name);
        localHashMap.put("icon", FileUtils.getBitmapDrawable(localInputStream, 80, 90));
        localHashMap.put("bkg", localPageBkg);
        if (localPageBkg.id == paramInt)
          localObject1 = localPageBkg;
        localInputStream.close();
      }
    }
    catch (IOException localIOException2)
    {
      localObject3 = localObject1;
      localObject2 = localIOException2;
    }
    ((IOException)localObject2).printStackTrace();
    return (PageBkg) localObject3;
  }
  catch (IOException localIOException1)
  {
    while (true)
    {
      Object localObject2 = localIOException1;
      Object localObject3 = null;
    }
  }
}

public static List getBkgs(InputStream paramInputStream)
{
  return a(paramInputStream);
}

public Bitmap getBitmap(Context paramContext)
{
  try
  {
    Bitmap localBitmap = FileUtils.getBitmap2(paramContext.getAssets().open("bkg/" + this.name), width, height);
    return localBitmap;
  }
  catch (IOException localIOException)
  {
    localIOException.printStackTrace();
  }
  return null;
}

public void setId(int paramInt)
{
  this.id = paramInt;
}

public void setName(String paramString)
{
  this.name = paramString;
}
}