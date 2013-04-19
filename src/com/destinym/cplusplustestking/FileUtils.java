package com.destinym.cplusplustestking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class FileUtils
{
	public  enum FileType implements Serializable
	{
		  //static
//		    IMAGE = new FileType("IMAGE", 1);
//		    AUDIO = new FileType("AUDIO", 2);
//		    VIDEO = new FileType("VIDEO", 3);
//		    UNKNOWN = new FileType("UNKNOWN", 4);
//		    FileType[] arrayOfFileType = new FileType[5];
//		    arrayOfFileType[0] = TXT;
//		    arrayOfFileType[1] = IMAGE;
//		    arrayOfFileType[2] = AUDIO;
//		    arrayOfFileType[3] = VIDEO;
//		    arrayOfFileType[4] = UNKNOWN;

	};

	public static final String ROM_STORE_PATH = "data";
	public static final String ROM_TEMP_PATH = "temp";
	public static final String SDCARD_STORE_PATH = "beans/data/";
	public static final String SDCARD_TEMP_PATH = "beans/temp/";
	public static String mRomStorePath = "/data/data/com.liveinlife/beans/";
	public static String mRomTempPath = "/data/data/com.liveinlife/beans/";
	private static Bitmap localBitmap1;

	public static int ComputeSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
	{
		int i = 1;
		double d1 = paramOptions.outWidth;
		double d2 = paramOptions.outHeight;
		int j = 0;
		int k;
		if (paramInt2 == -1)
		{
			j = i;
			if (paramInt1 != -1)
				;
			//break label76;
			k = 128;
			label34: 
				if (k >= j)
					;
			//break label101;
			label41: 
				if (j > 8)
					//break label136;
					;
		}

		while (true)
		{
			if (i >= j)
			{
				//return i;
				j = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
				//break;
				//label76:
				k = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
				//break label34;
				label101: 
					if ((paramInt2 == -1) && (paramInt1 == -1))
					{
						j = i;
						//break label41;
					}
				if (paramInt1 == -1)
					;//break label41;
				j = k;
				;//break label41;
			}
			i <<= 1;
		//;label136: 
			return 8 * ((j + 7) / 8);
		}
	}

	public static Bitmap DecodeBitmapFile(String paramString, int paramInt1, int paramInt2)
	{
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(paramString, localOptions);
		localOptions.inSampleSize = ComputeSize(localOptions, -1, paramInt1 * paramInt2);
		localOptions.inJustDecodeBounds = false;
		try
		{
			Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
			return localBitmap;
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
		}
		return null;
	}

	public static Bitmap decodeFile(File paramFile, int paramInt)
	{
		try
		{
			BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
			localOptions1.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(paramFile), null, localOptions1);
			Double localDouble = Double.valueOf(1.0D);
			if ((localOptions1.outHeight > paramInt) || (localOptions1.outWidth > paramInt))
				localDouble = Double.valueOf(Math.pow(2.0D, (int)Math.round(Math.log(paramInt / Math.max(localOptions1.outHeight, localOptions1.outWidth)) / Math.log(0.5D))));
			BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
			localOptions2.inSampleSize = localDouble.intValue();
			Bitmap localBitmap = BitmapFactory.decodeStream(new FileInputStream(paramFile), null, localOptions2);
			return localBitmap;
		}
		catch (FileNotFoundException localFileNotFoundException)
		{
		}
		return null;
	}

	public static String extractFileExt(String paramString)
	{
		if (paramString == null);
		String str;
		int i;
		do
		{
			str = paramString.replace('\\', '/');
			i = str.lastIndexOf(".");
		}
		while (i == -1);
		return str.substring(i + 1, str.length());
	}

	public static String extractFileName(String paramString)
	{
		if (paramString == null)
			return null;
		String str = paramString.replace('\\', '/');
		int i = str.lastIndexOf("/");
		if (i == -1);
		for (int j = 0; ; j = i + 1)
			return str.substring(j, str.length());
	}

	public static String extractFileNameNoExt(String paramString)
	{
		if (paramString == null)
			return null;
		String str = paramString.replace('\\', '/');
		int i = str.lastIndexOf(".");
		if (i == -1)
			i = str.length();
		return str.substring(0, i);
	}

	public static String extractLastDirName(String paramString)
	{
		if (paramString == null)
			return null;
		String str1 = paramString.replace('\\', '/');
		int i = str1.lastIndexOf("/");
		if (i == -1);
		for (int j = 0; ; j = i + 1)
		{
			String str2 = str1.substring(j, str1.length());
			if (str2 == null)
				str2 = "";
			if (str2.trim().length() <= 0)
				break;
			return str2.trim();
		}
		if (i < 0)
			return "";
		int k = str1.lastIndexOf("/", i - 1);
		int m = 0;
		if (k == -1);
		while (true)
		{
			
			m = k + 1;
			return str1.substring(m, i).trim();
		}
	}

	public static String extractUrlDir(String paramString)
	{
		if (paramString == null)
			return null;
		String str = paramString.replace('\\', '/');
		int i = str.lastIndexOf("/");
		if (i == -1)
			return "";
		return str.substring(0, i + 1);
	}

	// ERROR //
	public static boolean fileCopy(String paramString1, String paramString2)
	{
		return false;
		// Byte code:
		//   0: aconst_null
		//   1: astore_2
		//   2: iconst_0
		//   3: istore_3
		//   4: new 159	java/io/File
		//   7: dup
		//   8: aload_0
		//   9: invokespecial 162	java/io/File:<init>	(Ljava/lang/String;)V
		//   12: astore 4
		//   14: aload 4
		//   16: invokevirtual 166	java/io/File:exists	()Z
		//   19: ifne +27 -> 46
		//   22: ldc 168
		//   24: new 170	java/lang/StringBuilder
		//   27: dup
		//   28: ldc 172
		//   30: invokespecial 173	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
		//   33: aload_0
		//   34: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
		//   37: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
		//   40: invokestatic 186	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
		//   43: pop
		//   44: iconst_0
		//   45: ireturn
		//   46: new 159	java/io/File
		//   49: dup
		//   50: aload_1
		//   51: invokespecial 162	java/io/File:<init>	(Ljava/lang/String;)V
		//   54: astore 5
		//   56: aload 4
		//   58: invokevirtual 189	java/io/File:isFile	()Z
		//   61: ifeq +329 -> 390
		//   64: aload 5
		//   66: invokevirtual 192	java/io/File:isDirectory	()Z
		//   69: ifeq +155 -> 224
		//   72: new 82	java/io/FileInputStream
		//   75: dup
		//   76: aload 4
		//   78: invokespecial 85	java/io/FileInputStream:<init>	(Ljava/io/File;)V
		//   81: astore 25
		//   83: new 194	java/io/FileOutputStream
		//   86: dup
		//   87: new 159	java/io/File
		//   90: dup
		//   91: new 170	java/lang/StringBuilder
		//   94: dup
		//   95: aload_1
		//   96: invokestatic 197	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
		//   99: invokespecial 173	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
		//   102: aload 4
		//   104: invokevirtual 200	java/io/File:getName	()Ljava/lang/String;
		//   107: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
		//   110: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
		//   113: invokespecial 162	java/io/File:<init>	(Ljava/lang/String;)V
		//   116: invokespecial 201	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
		//   119: astore 26
		//   121: aload 4
		//   123: invokevirtual 204	java/io/File:length	()J
		//   126: l2i
		//   127: newarray byte
		//   129: astore 31
		//   131: aload 25
		//   133: aload 31
		//   135: invokevirtual 208	java/io/FileInputStream:read	([B)I
		//   138: pop
		//   139: aload 26
		//   141: aload 31
		//   143: invokevirtual 212	java/io/FileOutputStream:write	([B)V
		//   146: aload 25
		//   148: invokevirtual 215	java/io/FileInputStream:close	()V
		//   151: aload 26
		//   153: invokevirtual 216	java/io/FileOutputStream:close	()V
		//   156: iconst_1
		//   157: ireturn
		//   158: astore 27
		//   160: aconst_null
		//   161: astore 26
		//   163: aload 27
		//   165: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   168: aload_2
		//   169: invokevirtual 215	java/io/FileInputStream:close	()V
		//   172: aload 26
		//   174: invokevirtual 216	java/io/FileOutputStream:close	()V
		//   177: goto -21 -> 156
		//   180: astore 30
		//   182: aload 30
		//   184: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   187: iconst_0
		//   188: ireturn
		//   189: astore 28
		//   191: aconst_null
		//   192: astore 25
		//   194: aload 25
		//   196: invokevirtual 215	java/io/FileInputStream:close	()V
		//   199: aload_2
		//   200: invokevirtual 216	java/io/FileOutputStream:close	()V
		//   203: aload 28
		//   205: athrow
		//   206: astore 29
		//   208: aload 29
		//   210: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   213: iconst_0
		//   214: ireturn
		//   215: astore 33
		//   217: aload 33
		//   219: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   222: iconst_0
		//   223: ireturn
		//   224: new 82	java/io/FileInputStream
		//   227: dup
		//   228: aload 4
		//   230: invokespecial 85	java/io/FileInputStream:<init>	(Ljava/io/File;)V
		//   233: astore 12
		//   235: aload 5
		//   237: invokevirtual 166	java/io/File:exists	()Z
		//   240: ifne +35 -> 275
		//   243: aload 5
		//   245: invokevirtual 223	java/io/File:getParentFile	()Ljava/io/File;
		//   248: astore 22
		//   250: aload 22
		//   252: ifnull +17 -> 269
		//   255: aload 22
		//   257: invokevirtual 166	java/io/File:exists	()Z
		//   260: ifne +9 -> 269
		//   263: aload 22
		//   265: invokevirtual 226	java/io/File:mkdirs	()Z
		//   268: pop
		//   269: aload 5
		//   271: invokevirtual 229	java/io/File:createNewFile	()Z
		//   274: pop
		//   275: new 194	java/io/FileOutputStream
		//   278: dup
		//   279: aload 5
		//   281: invokespecial 201	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
		//   284: astore 18
		//   286: aload 4
		//   288: invokevirtual 204	java/io/File:length	()J
		//   291: l2i
		//   292: newarray byte
		//   294: astore 19
		//   296: aload 12
		//   298: aload 19
		//   300: invokevirtual 208	java/io/FileInputStream:read	([B)I
		//   303: pop
		//   304: aload 18
		//   306: aload 19
		//   308: invokevirtual 212	java/io/FileOutputStream:write	([B)V
		//   311: aload 12
		//   313: invokevirtual 215	java/io/FileInputStream:close	()V
		//   316: aload 18
		//   318: invokevirtual 216	java/io/FileOutputStream:close	()V
		//   321: goto -165 -> 156
		//   324: astore 21
		//   326: aload 21
		//   328: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   331: iconst_0
		//   332: ireturn
		//   333: astore 13
		//   335: aconst_null
		//   336: astore 14
		//   338: aload 13
		//   340: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   343: aload 14
		//   345: invokevirtual 215	java/io/FileInputStream:close	()V
		//   348: aload_2
		//   349: invokevirtual 216	java/io/FileOutputStream:close	()V
		//   352: goto -196 -> 156
		//   355: astore 17
		//   357: aload 17
		//   359: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   362: iconst_0
		//   363: ireturn
		//   364: astore 15
		//   366: aconst_null
		//   367: astore 12
		//   369: aload 12
		//   371: invokevirtual 215	java/io/FileInputStream:close	()V
		//   374: aload_2
		//   375: invokevirtual 216	java/io/FileOutputStream:close	()V
		//   378: aload 15
		//   380: athrow
		//   381: astore 16
		//   383: aload 16
		//   385: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   388: iconst_0
		//   389: ireturn
		//   390: aload 4
		//   392: invokevirtual 192	java/io/File:isDirectory	()Z
		//   395: ifeq -239 -> 156
		//   398: aload 5
		//   400: invokevirtual 166	java/io/File:exists	()Z
		//   403: ifne +35 -> 438
		//   406: aload 5
		//   408: invokevirtual 223	java/io/File:getParentFile	()Ljava/io/File;
		//   411: astore 9
		//   413: aload 9
		//   415: ifnull +17 -> 432
		//   418: aload 9
		//   420: invokevirtual 166	java/io/File:exists	()Z
		//   423: ifne +9 -> 432
		//   426: aload 9
		//   428: invokevirtual 226	java/io/File:mkdirs	()Z
		//   431: pop
		//   432: aload 5
		//   434: invokevirtual 229	java/io/File:createNewFile	()Z
		//   437: pop
		//   438: aload 4
		//   440: invokevirtual 233	java/io/File:list	()[Ljava/lang/String;
		//   443: astore 6
		//   445: iload_3
		//   446: aload 6
		//   448: arraylength
		//   449: if_icmpge -293 -> 156
		//   452: new 170	java/lang/StringBuilder
		//   455: dup
		//   456: aload 4
		//   458: invokevirtual 236	java/io/File:getPath	()Ljava/lang/String;
		//   461: invokestatic 197	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
		//   464: invokespecial 173	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
		//   467: ldc 141
		//   469: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
		//   472: aload 6
		//   474: iload_3
		//   475: aaload
		//   476: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
		//   479: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
		//   482: new 170	java/lang/StringBuilder
		//   485: dup
		//   486: aload 5
		//   488: invokevirtual 236	java/io/File:getPath	()Ljava/lang/String;
		//   491: invokestatic 197	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
		//   494: invokespecial 173	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
		//   497: ldc 141
		//   499: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
		//   502: aload 6
		//   504: iload_3
		//   505: aaload
		//   506: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
		//   509: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
		//   512: invokestatic 238	com/ldm/utils/FileUtils:fileCopy	(Ljava/lang/String;Ljava/lang/String;)Z
		//   515: pop
		//   516: iinc 3 1
		//   519: goto -74 -> 445
		//   522: astore 8
		//   524: aload 8
		//   526: invokevirtual 219	java/io/IOException:printStackTrace	()V
		//   529: goto -91 -> 438
		//   532: astore 15
		//   534: aconst_null
		//   535: astore_2
		//   536: goto -167 -> 369
		//   539: astore 15
		//   541: aload 18
		//   543: astore_2
		//   544: goto -175 -> 369
		//   547: astore 15
		//   549: aload 14
		//   551: astore 12
		//   553: goto -184 -> 369
		//   556: astore 13
		//   558: aload 12
		//   560: astore 14
		//   562: aconst_null
		//   563: astore_2
		//   564: goto -226 -> 338
		//   567: astore 13
		//   569: aload 18
		//   571: astore_2
		//   572: aload 12
		//   574: astore 14
		//   576: goto -238 -> 338
		//   579: astore 28
		//   581: aconst_null
		//   582: astore_2
		//   583: goto -389 -> 194
		//   586: astore 28
		//   588: aload 26
		//   590: astore_2
		//   591: goto -397 -> 194
		//   594: astore 28
		//   596: aload_2
		//   597: astore 25
		//   599: aload 26
		//   601: astore_2
		//   602: goto -408 -> 194
		//   605: astore 27
		//   607: aload 25
		//   609: astore_2
		//   610: aconst_null
		//   611: astore 26
		//   613: goto -450 -> 163
		//   616: astore 27
		//   618: aload 25
		//   620: astore_2
		//   621: goto -458 -> 163
		//
		// Exception table:
		//   from	to	target	type
		//   72	83	158	java/io/IOException
		//   168	177	180	java/io/IOException
		//   72	83	189	finally
		//   194	203	206	java/io/IOException
		//   146	156	215	java/io/IOException
		//   311	321	324	java/io/IOException
		//   224	235	333	java/io/IOException
		//   343	352	355	java/io/IOException
		//   224	235	364	finally
		//   369	378	381	java/io/IOException
		//   406	413	522	java/io/IOException
		//   418	432	522	java/io/IOException
		//   432	438	522	java/io/IOException
		//   235	250	532	finally
		//   255	269	532	finally
		//   269	275	532	finally
		//   275	286	532	finally
		//   286	311	539	finally
		//   338	343	547	finally
		//   235	250	556	java/io/IOException
		//   255	269	556	java/io/IOException
		//   269	275	556	java/io/IOException
		//   275	286	556	java/io/IOException
		//   286	311	567	java/io/IOException
		//   83	121	579	finally
		//   121	146	586	finally
		//   163	168	594	finally
		//   83	121	605	java/io/IOException
		//   121	146	616	java/io/IOException
	}

	public static void fileDelete(String paramString)
	{
		File localFile = new File(paramString);
		if (!localFile.exists());
		do
		{
			
			if (localFile.isFile())
			{
				localFile.delete();
				return;
			}
		}
		while (!localFile.isDirectory());
		String[] arrayOfString = localFile.list();
		for (int i = 0; ; i++)
		{
			if (i >= arrayOfString.length)
			{
				localFile.delete();
				return;
			}
			fileDelete(localFile.getPath() + "/" + arrayOfString[i]);
		}
	}

	// ERROR //
	public static Bitmap getBitmap(InputStream paramInputStream, int paramInt1, int paramInt2)
	{
		Bitmap map = null;
		return map;
				
		// Byte code:
		//   0: new 34	android/graphics/BitmapFactory$Options
		//   3: dup
		//   4: invokespecial 62	android/graphics/BitmapFactory$Options:<init>	()V
		//   7: astore_3
		//   8: aload_3
		//   9: iconst_1
		//   10: putfield 66	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
		//   13: aload_0
		//   14: invokestatic 249	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
		//   17: pop
		//   18: new 251	android/graphics/Rect
		//   21: dup
		//   22: iconst_1
		//   23: iconst_1
		//   24: iconst_1
		//   25: iconst_1
		//   26: invokespecial 254	android/graphics/Rect:<init>	(IIII)V
		//   29: astore 5
		//   31: aload_0
		//   32: aload 5
		//   34: aload_3
		//   35: invokestatic 89	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
		//   38: astore 6
		//   40: aload_3
		//   41: getfield 41	android/graphics/BitmapFactory$Options:outHeight	I
		//   44: istore 7
		//   46: aload_3
		//   47: getfield 38	android/graphics/BitmapFactory$Options:outWidth	I
		//   50: istore 8
		//   52: iload 7
		//   54: iload_2
		//   55: if_icmplt +9 -> 64
		//   58: iload 8
		//   60: iload_1
		//   61: if_icmpge +32 -> 93
		//   64: aload_3
		//   65: aload_3
		//   66: iconst_m1
		//   67: iload_1
		//   68: iload_2
		//   69: imul
		//   70: invokestatic 74	com/ldm/utils/FileUtils:ComputeSize	(Landroid/graphics/BitmapFactory$Options;II)I
		//   73: putfield 77	android/graphics/BitmapFactory$Options:inSampleSize	I
		//   76: aload_3
		//   77: iconst_0
		//   78: putfield 66	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
		//   81: aload_0
		//   82: aload 5
		//   84: aload_3
		//   85: invokestatic 89	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
		//   88: astore 10
		//   90: aload 10
		//   92: areturn
		//   93: aload_0
		//   94: invokestatic 249	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
		//   97: astore 12
		//   99: aload 12
		//   101: areturn
		//   102: astore 9
		//   104: aload 6
		//   106: areturn
		//   107: astore 11
		//   109: aload 6
		//   111: areturn
		//
		// Exception table:
		//   from	to	target	type
		//   81	90	102	java/lang/OutOfMemoryError
		//   93	99	107	java/lang/OutOfMemoryError
	}

	public static Bitmap getBitmap2(InputStream paramInputStream, int paramInt1, int paramInt2)
	{
		Bitmap localBitmap1;
		try
		{
			Bitmap localBitmap3 = BitmapFactory.decodeStream(paramInputStream);
			localBitmap1 = localBitmap3;
			int i = localBitmap1.getWidth();
			int j = localBitmap1.getHeight();
			double d1 = 1.0D * paramInt1 / (1.0D * i);
			double d2 = 1.0D * paramInt2 / (1.0D * j);
			Log.d("FileUtils", "wScale: " + d1 + "hScale: " + d2);
			Matrix localMatrix = new Matrix();
			localMatrix.postScale((float)d1, (float)d2);
			Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, i, j, localMatrix, true);
			Log.d("FileUtils", "resizeBmpW: " + localBitmap2.getWidth() + "resizeBmpH: " + localBitmap2.getHeight());
			return localBitmap2;
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
			while (true)
				 localBitmap1 = null;
		}
	}

	public static BitmapDrawable getBitmapDrawable(InputStream paramInputStream)
	{
		Bitmap localBitmap1;
		try
		{
			Bitmap localBitmap2 = BitmapFactory.decodeStream(paramInputStream);
			localBitmap1 = localBitmap2;
			return new BitmapDrawable(localBitmap1);
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
			while (true)
				 localBitmap1 = null;
		}
	}

	public static BitmapDrawable getBitmapDrawable(InputStream paramInputStream, int paramInt1, int paramInt2)
	{
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(paramInputStream);
		Rect localRect = new Rect(1, 1, 1, 1);
		BitmapFactory.decodeStream(paramInputStream, localRect, localOptions);
		localOptions.inSampleSize = ComputeSize(localOptions, -1, paramInt1 * paramInt2);
		localOptions.inJustDecodeBounds = false;
		Bitmap localBitmap1;
		try
		{
			Bitmap localBitmap2 = BitmapFactory.decodeStream(paramInputStream, localRect, localOptions);
			localBitmap1 = localBitmap2;
			return new BitmapDrawable(localBitmap1);
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
			while (true)
				 localBitmap1 = null;
		}
	}

	public static BitmapDrawable getBitmapDrawable(String paramString)
	{
		try
		{
			Bitmap localBitmap2 = BitmapFactory.decodeFile(paramString);
			localBitmap1 = localBitmap2;
			return new BitmapDrawable(localBitmap1);
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
			while (true)
			 localBitmap1 = null;
		}
	}

	public static BitmapDrawable getBitmapDrawable(String paramString, int paramInt1, int paramInt2)
	{
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(paramString, localOptions);
		localOptions.inSampleSize = ComputeSize(localOptions, -1, paramInt1 * paramInt2);
		localOptions.inJustDecodeBounds = false;
		try
		{
			Bitmap localBitmap2 = BitmapFactory.decodeFile(paramString, localOptions);
			localBitmap1 = localBitmap2;
			return new BitmapDrawable(localBitmap1);
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
			while (true)
				 localBitmap1 = null;
		}
	}

	public static BitmapDrawable getBitmapDrawable2(InputStream paramInputStream, int paramInt1, int paramInt2)
	{
		try
		{
			Bitmap localBitmap3 = BitmapFactory.decodeStream(paramInputStream);
			localBitmap1 = localBitmap3;
			int i = localBitmap1.getWidth();
			int j = localBitmap1.getHeight();
			double d1 = 1.0D * paramInt1 / (1.0D * i);
			double d2 = 1.0D * paramInt2 / (1.0D * j);
			Log.d("FileUtils", "wScale: " + d1 + "hScale: " + d2);
			Matrix localMatrix = new Matrix();
			localMatrix.postScale((float)d1, (float)d2);
			Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, i, j, localMatrix, true);
			Log.d("FileUtils", "resizeBmpW: " + localBitmap2.getWidth() + "resizeBmpH: " + localBitmap2.getHeight());
			return new BitmapDrawable(localBitmap2);
		}
		catch (OutOfMemoryError localOutOfMemoryError)
		{
			while (true)
				 localBitmap1 = null;
		}
	}

	public static String getCityName(Context paramContext)
	{
		Location localLocation = ((LocationManager)paramContext.getSystemService("location")).getLastKnownLocation("network");
		if (localLocation != null)
		{
			double d1 = localLocation.getLatitude();
			double d2 = localLocation.getLongitude();
			Geocoder localGeocoder = new Geocoder(paramContext, Locale.ENGLISH);
			try
			{
				List localList = localGeocoder.getFromLocation(d1, d2, 1);
				StringBuilder localStringBuilder = new StringBuilder();
				if (localList.size() > 0)
				{
					localStringBuilder.append(((Address)localList.get(0)).getLocality()).append("\n");
					String str = localStringBuilder.toString();
					return str;
				}
			}
			catch (IOException localIOException)
			{
			}
		}
		return "";
	}

	public static FileUtils.FileType getFileType(String paramString)
	{
		String str = paramString.toLowerCase();
		if ((str.endsWith(".jpg")) || (str.endsWith(".bmp")) || (str.endsWith(".gif")) || (str.endsWith(".jpeg")) || (str.endsWith(".png")))
			;//return FileUtils.FileType.IMAGE;
		if (str.endsWith(".txt"))
			;//return FileUtils.FileType.TXT;
		if ((str.endsWith(".mp3")) || (str.endsWith(".wav")) || (str.endsWith(".amr")) || (str.endsWith(".mid")))
			;//return FileUtils.FileType.AUDIO;
		if ((str.endsWith(".mp4")) || (str.endsWith(".mpeg")) || (str.endsWith(".3gp")))
			;//return FileUtils.FileType.VIDEO;
		//return FileUtils.FileType.UNKNOWN;
		return null;
	}

	public static String getRootPath(Context paramContext)
	{
		String str = paramContext.getDir("data", 0).getAbsolutePath() + "/";
		mRomStorePath = str;
		return str;
	}

	public static String getSdCardPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getSdCardRootPath()
	{
		return makeFullPath(getSdCardPath(), "beans/data/");
	}

	public static String getStorePath(Context paramContext)
	{
		if (Environment.getExternalStorageState().equals("mounted"))
			return getSdCardRootPath();
		return getRootPath(paramContext);
	}

	public static String getTempPath(Context paramContext)
	{
		if (Environment.getExternalStorageState().equals("mounted"))
			return makeFullPath(getSdCardPath(), "beans/temp/");
		mRomTempPath = paramContext.getDir("temp", 0).getAbsolutePath() + "/";
		return mRomStorePath;
	}

	public static void init(Context paramContext)
	{
	}

	public static boolean isSdCardExists()
	{
		return Environment.getExternalStorageState().equals("mounted");
	}

	public static String makeFullPath(String paramString1, String paramString2)
	{
		if ((paramString1.endsWith("/")) || (paramString2.startsWith("/")))
			return paramString1 + paramString2;
		return paramString1 + "/" + paramString2;
	}
}