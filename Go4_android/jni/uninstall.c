#include <stdio.h>
#include <jni.h>
#include <malloc.h>
#include <string.h>
#include <strings.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/inotify.h>
#include <fcntl.h>
#include <stdint.h>
#include "com_ciyun_lovehealth_HealthApplication.h"
#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/**
 * 返回值 char* 这个代表char数组的首地址
 * Jstring2CStr 把java中的jstring的类型转化成一个c语言中的char 字符串
 */
char* Jstring2CStr(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String"); //String
	jstring strencode = (*env)->NewStringUTF(env, "GB2312"); // 得到一个java字符串 "GB2312"
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B"); //[ String.getBytes("gb2312");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
			strencode); // String .getByte("GB2312");
	jsize alen = (*env)->GetArrayLength(env, barr); // byte数组的长度
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1); //"\0"
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0); //
	return rtn;
}

JNIEXPORT void JNICALL Java_com_ciyun_lovehealth_HealthApplication_uninstall(
		JNIEnv * env, jobject obj, jstring packageDir, jint sdkVersion) {
	// 1，将传递过来的java的包名转为c的字符串
	char * pd = Jstring2CStr(env, packageDir);

	// 2，创建当前进程的克隆进程
	pid_t pid = fork();

	// 3，根据返回值的不同做不同的操作,<0,>0,=0
	if (pid < 0) {
		// 说明克隆进程失败
//		LOGD("current crate process failure");
	} else if (pid > 0) {
		// 说明克隆进程成功，而且该代码运行在父进程中
//		LOGD("crate process success,current parent pid = %d", pid);
	} else {
		// 说明克隆进程成功，而且代码运行在子进程中
//		LOGD("crate process success,current child pid = %d", pid);

		// 4，在子进程中监视/data/data/包名这个目录
		//初始化inotify进程
		int fd = inotify_init();
		if (fd < 0) {
//			LOGD("inotify_init failed !!!");
			exit(1);
		}

		//添加inotify监听器
		int wd = inotify_add_watch(fd, pd, IN_DELETE);
		if (wd < 0) {
//			LOGD("inotify_add_watch failed !!!");
			exit(1);
		}

		//分配缓存，以便读取event，缓存大小=一个struct inotify_event的大小，这样一次处理一个event
		void *p_buf = malloc(sizeof(struct inotify_event));
		if (p_buf == NULL) {
//			LOGD("malloc failed !!!");
			exit(1);
		}

		//开始监听
//		LOGD("start observer");
		ssize_t readBytes = read(fd, p_buf,sizeof(struct inotify_event));

		//read会阻塞进程，走到这里说明收到目录被删除的事件，注销监听器
		free(p_buf);
		inotify_rm_watch(fd, IN_DELETE);

		// 应用被卸载了，通知系统打开用户反馈的网页
//		LOGD("app uninstall,current sdkversion = %d", sdkVersion);
		if (sdkVersion >= 17) {
			// Android4.2系统之后支持多用户操作，所以得指定用户
			execlp("am", "am", "start", "--user", "0", "-a",
					"android.intent.action.VIEW", "-d", "http://www.baidu.com",
					(char*) NULL);
		} else {
			// Android4.2以前的版本无需指定用户
			execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
					"-d", "http://www.baidu.com", (char*) NULL);
		}

	}
}


