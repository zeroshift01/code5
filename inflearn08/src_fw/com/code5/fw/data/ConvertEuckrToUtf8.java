package com.code5.fw.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author zero
 *
 */
public class ConvertEuckrToUtf8 {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File file = new File("C:\\IMSI\\src");

		xx(file.listFiles());

	}

	/**
	 * @param files
	 * @throws Exception
	 */
	private static void xx(File[] files) throws Exception {

		if (files == null) {
			return;
		}

		for (int i = 0; i < files.length; i++) {

			File thisFile = files[i];

			if (thisFile.isDirectory()) {
				xx(thisFile.listFiles());
				continue;
			}

			String thisName = thisFile.getAbsolutePath();

			if (thisName.endsWith(".java")) {
				x(thisName);
				continue;
			}

			if (thisName.endsWith(".jsp")) {
				x(thisName);
				continue;
			}

		}

	}

	private static void x(String fileUrl) throws Exception {

		File file = new File(fileUrl);
		boolean isRename = file.renameTo(new File(fileUrl + ".euckr"));
		if (!isRename) {
			System.out.println("fail backup [" + fileUrl + "]");
			return;
		}

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileUrl + ".euckr"), "EUC-KR"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileUrl), "utf-8"));
		String line;
		while ((line = br.readLine()) != null) {
			String utf8 = new String(line.getBytes(), "UTF-8");
			// System.out.println(utf8);
			bw.write(utf8);
			bw.newLine();
		}
		bw.flush();
		br.close();
		bw.close();

		System.out.println("convert [" + fileUrl + "]");

	}
}
