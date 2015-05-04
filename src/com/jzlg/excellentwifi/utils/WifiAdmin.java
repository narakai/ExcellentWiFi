package com.jzlg.excellentwifi.utils;

import java.util.List;

import com.jzlg.excellentwifi.R;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SSID:Service Set ID ����ʶ���� WEP:Wired Equivalent Privacy���߰�ȫ
 * 2��ǿ�ȣ�40bits��104bits WEP2��128bit WAP/WAP2���߰�ȫ WPS WMM
 * 
 * @author Administrator
 *
 */
public class WifiAdmin {
	// ����һ��WifiManager
	private WifiManager mWifiManager;
	// ����һ��WifiInfo����
	private WifiInfo mWifiInfo;
	// ɨ��������������б�
	private List<ScanResult> mWifiList;
	// ���������б�
	private List<WifiConfiguration> mWifiConfigurations;
	// WifiLock[��ֹWifi����˯��״̬��ʹWifiһֱ���ڻ�Ծ״̬]
	WifiLock mWifiLock;
	private Context mContext;
	private WifiConfiguration mWifiConfig;
	private View mShowCView;
	private PopupWindow mShowCPopu = null;

	public WifiAdmin(Context context) {
		// ��ȡWifiManager����
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// ��ȡWifiInfo����
		mWifiInfo = mWifiManager.getConnectionInfo();
		mContext = context;
	}
	/**
	 * ��wifi
	 */
	public void openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	/**
	 * �ر�wifi
	 */
	public void stopWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * ���Wifi��ǰ״̬
	 * 
	 * @return Wifi״̬��
	 */
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	/**
	 * ����WifiLock
	 */
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	/**
	 * �ͷ�WifiLock
	 */
	public void releaseWifiLock() {
		// �ж��Ƿ�����
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	/**
	 * ����һ��WifiLock
	 */
	public void createWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("test");
	}

	/**
	 * �õ����úõ�����
	 * 
	 * @return WifiConfiguration
	 */
	public List<WifiConfiguration> getConfiguration() {
		return mWifiConfigurations;
	}

	/**
	 * ָ�����úõ������������
	 * 
	 * @param index
	 *            ID
	 */
	public void connetionConfiguration(int index) {
		if (index > mWifiConfigurations.size()) {
			return;
		}
		// �������ú�ָ��ID����
		mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,
				true);
	}

	/**
	 * ��ʼɨ������
	 */
	public void startScan() {
		mWifiManager.startScan();
		// �õ�ɨ����
		mWifiList = mWifiManager.getScanResults();
		// �õ����úõ���������
		mWifiConfigurations = mWifiManager.getConfiguredNetworks();
	}
	
	/**
	 * �����Ѿ����úõ�Wifi
	 * 
	 * @param ssid
	 * @return
	 */
	public boolean getHistoryWifiConfig(String ssid) {
		mWifiConfigurations = mWifiManager.getConfiguredNetworks();
		for (int i = 0; i < mWifiConfigurations.size(); i++) {
			if (("\"" + ssid + "\"").equals(mWifiConfigurations.get(i).SSID)) {
				connetionConfiguration(i);
				return false;
			}
		}
		return true;
	}

	/**
	 * �õ������б�
	 * 
	 * @return ScanResult
	 */
	public List<ScanResult> getWifiList() {
		return mWifiList;
	}
	
	/**
	 * ��ȡMacAddress
	 * 
	 * @return MacAddress
	 */
	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	/**
	 * ��ȡBSSID
	 * 
	 * @return BSSID
	 */
	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	/**
	 * ��ȡIP��ַ
	 * 
	 * @return IP��ַ
	 */
	public int getIpAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	/**
	 * ��ȡ���ӵ�ID
	 * 
	 * @return NetworkId
	 */
	public int getNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	/**
	 * ��ȡ�����ٶ�
	 * 
	 * @return LinkSpeed
	 */
	public int getLinkSpeed() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getLinkSpeed();
	}

	/**
	 * ��ȡWifiInfo��������Ϣ
	 * 
	 * @return WifiInfo��������Ϣ
	 */
	public String getWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	/**
	 * SSID�Ƿ�����
	 * 
	 * @return ���ط���true���򷵻�false��Ĭ����false
	 */
	public boolean getHiddenSSID() {
		return (mWifiInfo == null) ? false : mWifiInfo.getHiddenSSID();
	}

	/**
	 * ����һ�����粢����
	 * 
	 * @param wifiConfiguration
	 */
	public void addWifi(WifiConfiguration wifiConfiguration) {
		int wcgId = mWifiManager.addNetwork(wifiConfiguration);
		boolean boo = mWifiManager.enableNetwork(wcgId, true);
		if (!boo) {
			removeWifi(0);
			Toast.makeText(mContext, "�������", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ɾ�������ӵ�����
	 * 
	 * @param netId
	 */
	public void removeWifi(int netId) {
		mWifiManager.removeNetwork(netId);
	}

	/**
	 * ����һ�����粢����
	 * 
	 * @param wcg
	 */
	public void addNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		mWifiConfigurations = getConfiguration();
		mWifiManager.enableNetwork(wcgID, true);
	}

	/**
	 * �Ͽ�ָ��ID��������
	 * 
	 * @param netId
	 *            ����ID
	 */
	public void disConnectionWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

	/**
	 * ����wifi
	 * 
	 * @param position
	 */
	public void linkWifi(int position) {
		if (mWifiList != null) {
			String ssid = mWifiList.get(position).SSID;// ��ȡSSID
			boolean flag = getHistoryWifiConfig(ssid);// ���Wifi�����Ƿ��Ѵ���
			String capabilities = mWifiList.get(position).capabilities;// ��ȡ���ܷ�ʽ
			if (flag) {
				mWifiConfig = new WifiConfiguration();
				mWifiConfig.SSID = "\"" + ssid + "\"";
				mWifiConfig.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.CCMP);
				mWifiConfig.allowedGroupCiphers
						.set(WifiConfiguration.GroupCipher.CCMP);
				mWifiConfig.allowedGroupCiphers
						.set(WifiConfiguration.GroupCipher.TKIP);
				mWifiConfig.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.TKIP);
				if ("[ESS]".equals(capabilities)) {
					mWifiConfig.allowedKeyManagement
							.set(WifiConfiguration.KeyMgmt.NONE);
					mWifiConfig.allowedProtocols
							.set(WifiConfiguration.Protocol.WPA);
					mWifiConfig.wepTxKeyIndex = 0;
					addWifi(mWifiConfig);// ����ָ��Wiif
				} else if ("[WPA2-PSK-CCMP][ESS]".equals(capabilities)) {
					final LinearLayout llt = (LinearLayout) LayoutInflater
							.from(mContext).inflate(R.layout.wifi_login, null);
					new AlertDialog.Builder(mContext)
							.setIcon(R.drawable.ic_launcher)//
							.setTitle("��������")//
							.setView(llt)//
							.setPositiveButton("����",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// ����Ҫ���ӵ�����
											EditText et = (EditText) llt
													.findViewById(R.id.wifi_add_pwd);
											String pwd = et.getText()
													.toString();
											mWifiConfig.preSharedKey = "\""
													+ pwd + "\"";
											mWifiConfig.hiddenSSID = true;
											mWifiConfig.status = WifiConfiguration.Status.ENABLED;
											mWifiConfig.allowedKeyManagement
													.set(WifiConfiguration.KeyMgmt.WPA_PSK);
											mWifiConfig.allowedProtocols
													.set(WifiConfiguration.Protocol.RSN);
											addWifi(mWifiConfig);// ����ָ��Wifi
										}
									}).show();
				} else if ("[WPA2-PSK-CCMP][WPS][ESS]".equals(capabilities)) {
					final LinearLayout llt = (LinearLayout) LayoutInflater
							.from(mContext).inflate(R.layout.wifi_login, null);
					new AlertDialog.Builder(mContext)
							.setIcon(R.drawable.ic_launcher)
							.setTitle("��������")
							.setView(llt)
							.setPositiveButton("����",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// ����Ҫ���ӵ�����
											EditText et = (EditText) llt
													.findViewById(R.id.wifi_add_pwd);
											String pwd = et.getText()
													.toString();
											mWifiConfig.preSharedKey = "\""
													+ pwd + "\"";
											mWifiConfig.hiddenSSID = true;
											mWifiConfig.status = WifiConfiguration.Status.ENABLED;
											mWifiConfig.allowedKeyManagement
													.set(WifiConfiguration.KeyMgmt.WPA_PSK);
											mWifiConfig.allowedProtocols
													.set(WifiConfiguration.Protocol.RSN);
											addWifi(mWifiConfig);// ����ָ��Wifi
										}
									}).show();
				} else {

				}
			}
		}
	}

	public WifiConfiguration createWifiInfo(String SSID, String Password,
			int Type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		if (Type == 1)// Data.WIFICIPHER_NOPASS)
		{
//			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 2)// Data.WIFICIPHER_WEP)
		{
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + Password + "\"";
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 3)// Data.WIFICIPHER_WPA)
		{
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		return config;
	}
	
	public void showLoadingPop(final String ssid) {
		Button btn_ok, btn_cancel;
		TextView title;
		final EditText password;
		title = (TextView) mShowCView.findViewById(R.id.pop_title);
		password = (EditText) mShowCView.findViewById(R.id.pop_password);
		btn_ok = (Button) mShowCView.findViewById(R.id.pop_bt_ok);
		btn_cancel = (Button) mShowCView.findViewById(R.id.pop_bt_cancel);
		if (mShowCView == null) {
			mShowCView = LayoutInflater.from(mContext).inflate(
					R.layout.wifi_pop, null);
			mShowCView.setBackgroundResource(R.drawable.wifi_tv_show);
		}
		if (mShowCPopu == null) {
			mShowCPopu = new PopupWindow(mShowCView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		mShowCPopu.setFocusable(true);
		mShowCPopu.setOutsideTouchable(true);
		mShowCPopu.setBackgroundDrawable(new BitmapDrawable());
		title.setText("" + ssid);
		// ���Ӱ�ť
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String passwords = password.getText().toString().trim();
				if (passwords != null && !passwords.equals("")) {
					addNetwork(createWifiInfo(ssid, passwords, 3));
					// mWifiManager.enableNetwork(netid, true);
					mShowCPopu.dismiss();
				}

			}
		});
		// ȡ����ť
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				password.setText("");
				mShowCPopu.dismiss();
			}
		});
		mShowCPopu.showAtLocation(mShowCView, Gravity.CENTER, 0, 0);
	}

}