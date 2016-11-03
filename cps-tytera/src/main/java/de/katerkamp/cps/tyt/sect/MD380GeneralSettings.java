/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.ds.TalkPermitTone;
import de.katerkamp.cps.sect.GeneralSettings;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundString;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380GeneralSettings implements GeneralSettings {


	// Keep the order, its needed by Preon
	// 0x00
	@BoundString(size = "20", encoding = "UTF-16LE")  // 0x0  - 0x13
	private String infoScreenLine1; // bits 0 - 159
	// 0x14
	@BoundString(size = "20", encoding = "UTF-16LE") // 0x14 - 0x27
	private String infoScreenLine2; // 160 - 319

	@BoundString(size = "24") // 0x28 - 0x3f
	private String nn320; // 320 - 511

	// 0x40
	@Bound
	private boolean nn512; // 512 0x40
	@Bound
	private boolean nn513;
	@Bound
	private boolean nn514;

	// MonitorType 1 bit, 515 binary enum {0="silent" 1="open"}
	@Bound
	private boolean monitorTypeOpen;  // 515
	@Bound
	private boolean nn516;
	// DisableAllLeds 1 bit, 517 binary enum {0="off" 1="on"}
	@Bound
	private boolean disableAllLeds;  // 517
	@Bound
	private boolean nn518;
	@Bound
	private boolean nn519;

	// 0x41
	// TalkPermitTone        2 bit, 520 binary enum {0b00="none" 0b01="digital" 0b10="analog" 0b11="both"}
	//@BoundNumber(size = "2")
	private TalkPermitTone talkPermitTone; // 520 - 521 0 1
	@Bound
	private boolean tpz1; //520
	@Bound
	private boolean tpz2; //521

	// PasswordAndLockEnable 1 bit, 522 binary enum {1="off" 0="on"}
	@Bound
	private boolean passwordAndLockEnabled; //522 2
	// CHFreeIndicationTone  1 bit, 523 binary enum {1="off" 0="on"}
	@Bound
	private boolean chFreeIndicationToneOn; //523  3
	@Bound
	private boolean nn524; // 4
	// DisableAllTone        1 bit, 525 binary enum {1="off" 0="on"}
	@Bound
	private boolean disableAllTone;  // 525 4
	// SaveModeReceive       1 bit, 526 binary enum {0="off" 1="on"}
	@Bound
	private boolean saveModeReceive;  // 526 6
	// SavePreamble 1 bit, 527 binary enum {0="off" 1="on"}
	@Bound
	private boolean savePreamble; // 527 7

	// 0x42
	@Bound
	private boolean nn528;  // on byte bound bit 0
	@Bound
	private boolean nn529; // bit 1
	@Bound
	private boolean nn530; // bit 2
	// IntroScreen 1 bit, 531 binary enum {0="charstring" 1="picture"}
	@Bound
	private boolean pictureIntroScreen;  // 531  bit 3
	@Bound
	private boolean nn532; // bit 4
	@Bound
	private boolean nn533; // bit 5
	@Bound
	private boolean nn534; // bit 6
	@Bound
	private boolean nn535; // bit 7

	// 0x43
	// 6 7 8 9 540 541 542 543
	@BoundNumber(size = "8")
	private int nn536;  //nn 536 - 543 =  8 bits

	// 0x44 - 0x46
	// RadioId 24 bit, 544 0x44 binary comment %F%1max value is 16776415%0 %f or 32 bits?
	// validation %F%1if (rec->RadioId > 16776415) REPORT ("max is 16776415");%0
	@BoundNumber(size = "24")
	private int radioId;  // 544  - 

	@Bound
	private byte nnx47;

	// 0x48
	// TxPreamble  8 bit, 576 0x48 binary comment %F%1ms=N*60, where N<=0<=144%f%0
	// validation %F%1if (rec->TxPreamble > 144) REPORT ("max value is 144");%f%0
	@BoundNumber(size = "8")
	private int txPreamble;

	// 0x49
	// GroupCallHangTime     8 bit, 584 0x49 binary comment %F%1time in ms, ms=N*100, N<=70, N must be multiple of 5%f%0
	// validation %F%1if (rec->GroupCallHangTime > 70) REPORT ("max value is 70");
	// %f%0 %F%1if ((rec->GroupCallHangTime %% 5) != 0) REPORT ("must be multiple of 5");%0
	@BoundNumber(size = "8")
	private int groupCallHangTime;

	// 0x4a
	// PrivateCallHangTime   8 bit, 592 0x4a binary comment %F%1time in ms, ms=N*100, N<=70, N must be multiple of 5%f%0
	// validation %F%1if (rec->PrivateCallHangTime > 70) REPORT ("max value is 70");
	// %F%1if ((rec->PrivateCallHangTime %% 5) != 0) REPORT ("must be multiple of 5");%0
	@BoundNumber(size = "8")
	private int privateCallHangTime;

	// 0x4b
	// VoxSensitivity 8 bit, 600 0x4b binary validation %F%1if (rec->VoxSensitivity < 1 || rec->VoxSensitivity > 10) REPORT ("allowed range 1...10");%f%0
	@BoundNumber(size = "8")
	private int voxSensitivity;

	@Bound
	private byte nnx4c;
	@Bound
	private byte nnx4d;

	// RxLowBatteryInterval  8 bit, 624 0x4e binary comment %F%1time in seconds, s=N*5, N<=127%f%0
	// validation %F%1if (rec->TxPreamble > 127) REPORT ("max value is 127");%f%0
	@BoundNumber(size = "8")
	private int rxLowBatteryInterval;

	// CallAlertTone         8 bit, 632 0x4f binary comment %F%10=Continue, otherwise time in seconds, s=N*5, N<=240%f%0
	// validation %F%1if (rec->CallAlertTone > 240) REPORT ("max value is 240");%0
	@BoundNumber(size = "8")
	private int callAlertTone;

	// LoneWorkerRespTime    8 bit, 640 0x50 binary
	// validation %F%1if (rec->LoneWorkerRespTime < 1) REPORT ("min value is 1");%0
	@BoundNumber(size = "8")
	private int loneWorkerRespTime;

	// LoneWorkerReminderTime 8 bit, 648 0x51 binary
	// validation %F%1if (rec->LoneWorkerReminderTime < 1) REPORT ("min value is 1");%0
	@BoundNumber(size = "8")
	private int loneWorkerReminderTime;

	@Bound private byte nnx52;

	// ScanDigitalHangTime   8 bit, 664 0x53 binary comment %F%1time in ms, ms=N*5, 5<=N<=100; default N=10%f%0
	// validation %F%1if (rec->ScanDigitalHangTime < 5 || rec->ScanDigitalHangTime > 100 
	// || ((rec->ScanDigitalHangTime %% 5) != 0)) REPORT ("value between 5 and 100, multiple of 5");%f%0
	@BoundNumber(size = "8")
	private int scanDigitalHangTime;

	// ScanAnalogHangTime    8 bit, 672 0x54 binary comment %F%1time in ms, ms=N*5, 5<=N<=100; default N=10%f%0
	// validation %F%1if (rec->ScanAnalogHangTime < 5 || rec->ScanAnalogHangTime > 100 
	// || ((rec->ScanAnalogHangTime %% 5) != 0)) REPORT ("value between 5 and 100, multiple of 5");%f%0
	@BoundNumber(size = "8")
	private byte scanAnalogHangTime;

	// Unknown1 8 bit, 680 0x55 binary comment %F%1meaning still unknown, do not edit%f%0
	@BoundNumber(size = "8")
	private byte unknown1;

	// KeypadLockTime  8 bit, 688 0x56 binary enum {1="5s" 2="10s" 3="15s" 0xff="manual"}
	@BoundNumber(size = "8")
	private byte keypadLockTime;

	// Mode 8 bit, 696 0x57 binary enum {0x00="mr" 0xFF="ch"}
	@BoundNumber(size = "8")
	private byte mode;

	// PowerOnPassword 32 bit, 704 0x58 RevBCD comment %F%18 digits%f%0
	@BoundNumber(size = "32")
	private int powerOnPIN;

	// RadioProgPassowrd    32 bit, 736 0x5c RevBCD comment %F%18 digits%f%0
	// 12345678 would be 12 34 56 78
	@BoundNumber(size = "32")
	private int radioProgPIN;

	// ok bis hierher

	// PcProgPassword 64 bit, 768 0x60 
	// ascii format %F%1Converted to lower case; if not set, set to all 0xFF.%f%0
	@BoundString(size = "8")
	private String pcProgPassword;

	@BoundString(size = "8")
	private String nnx68;

	// RadioName  256 bit, 896 0x70
	@BoundString(size = "32", encoding = "UTF-16LE")
	private String radioName;


	// automatically created getters/setters below

	@Override
	public String getInfoScreenLine1() {
		return infoScreenLine1;
	}

	@Override
	public void setInfoScreenLine1(String infoScreenLine1) {
		if (infoScreenLine1 == null) {
			return;
		}
		this.infoScreenLine1 = infoScreenLine1.trim();
	}

	@Override
	public String getInfoScreenLine2() {
		return infoScreenLine2;
	}

	@Override
	public void setInfoScreenLine2(String infoScreenLine2) {
		if (infoScreenLine2 == null) {
			return;
		}
		this.infoScreenLine2 = infoScreenLine2.trim();
	}

	@Override
	public boolean isMonitorTypeOpen() {
		return monitorTypeOpen;
	}

	@Override
	public void setMonitorTypeOpen(boolean monitorTypeOpen) {
		this.monitorTypeOpen = monitorTypeOpen;
	}

	@Override
	public boolean isDisableAllLeds() {
		return disableAllLeds;
	}

	@Override
	public void setDisableAllLeds(boolean disableAllLeds) {
		this.disableAllLeds = disableAllLeds;
	}

	@Override
	public TalkPermitTone getTalkPermitTone() {
		return talkPermitTone;
	}

	@Override
	public void setTalkPermitTone(TalkPermitTone talkPermitTone) {
		this.talkPermitTone = TalkPermitTone.NONE; //   @todotalkPermitTone;
	}

	@Override
	public boolean isPasswordAndLockEnabled() {
		return passwordAndLockEnabled;
	}

	@Override
	public void setPasswordAndLockEnabled(boolean passwordAndLockEnabled) {
		this.passwordAndLockEnabled = passwordAndLockEnabled;
	}

	@Override
	public boolean isChFreeIndicationToneOn() {
		return chFreeIndicationToneOn;
	}

	@Override
	public void setChFreeIndicationToneOn(boolean chFreeIndicationToneOn) {
		this.chFreeIndicationToneOn = chFreeIndicationToneOn;
	}

	@Override
	public boolean isDisableAllTone() {
		return disableAllTone;
	}

	@Override
	public void setDisableAllTone(boolean disableAllTone) {
		this.disableAllTone = disableAllTone;
	}

	@Override
	public boolean isSaveModeReceive() {
		return saveModeReceive;
	}

	@Override
	public void setSaveModeReceive(boolean saveModeReceive) {
		this.saveModeReceive = saveModeReceive;
	}

	@Override
	public boolean isSavePreamble() {
		return savePreamble;
	}

	@Override
	public void setSavePreamble(boolean savePreamble) {
		this.savePreamble = savePreamble;
	}

	@Override
	public boolean isPictureIntroScreen() {
		return pictureIntroScreen;
	}

	@Override
	public void setPictureIntroScreen(boolean pictureIntroScreen) {
		this.pictureIntroScreen = pictureIntroScreen;
	}

	@Override
	public int getRadioId() {
		return radioId;
	}

	@Override
	public void setRadioId(int radioId) {
		this.radioId = radioId;
	}

	@Override
	public int getTxPreamble() {
		return txPreamble;
	}

	@Override
	public void setTxPreamble(byte txPreamble) {
		this.txPreamble = txPreamble;
	}

	@Override
	public int getGroupCallHangTime() {
		return groupCallHangTime;
	}

	@Override
	public void setGroupCallHangTime(byte groupCallHangTime) {
		this.groupCallHangTime = groupCallHangTime;
	}

	@Override
	public int getPrivateCallHangTime() {
		return privateCallHangTime;
	}

	@Override
	public void setPrivateCallHangTime(byte privateCallHangTime) {
		this.privateCallHangTime = privateCallHangTime;
	}

	@Override
	public int getVoxSensitivity() {
		return voxSensitivity;
	}

	@Override
	public void setVoxSensitivity(byte voxSensitivity) {
		this.voxSensitivity = voxSensitivity;
	}

	@Override
	public int getRxLowBatteryInterval() {
		return rxLowBatteryInterval;
	}

	@Override
	public void setRxLowBatteryInterval(byte rxLowBatteryInterval) {
		this.rxLowBatteryInterval = rxLowBatteryInterval;
	}

	@Override
	public int getCallAlertTone() {
		return callAlertTone;
	}

	@Override
	public void setCallAlertTone(byte callAlertTone) {
		this.callAlertTone = callAlertTone;
	}

	@Override
	public int getLoneWorkerRespTime() {
		return loneWorkerRespTime;
	}

	@Override
	public void setLoneWorkerRespTime(byte loneWorkerRespTime) {
		this.loneWorkerRespTime = loneWorkerRespTime;
	}

	@Override
	public int getLoneWorkerReminderTime() {
		return loneWorkerReminderTime;
	}

	@Override
	public void setLoneWorkerReminderTime(byte loneWorkerReminderTime) {
		this.loneWorkerReminderTime = loneWorkerReminderTime;
	}

	@Override
	public int getScanDigitalHangTime() {
		return scanDigitalHangTime;
	}

	@Override
	public void setScanDigitalHangTime(byte scanDigitalHangTime) {
		this.scanDigitalHangTime = scanDigitalHangTime;
	}

	@Override
	public byte getScanAnalogHangTime() {
		return scanAnalogHangTime;
	}

	@Override
	public void setScanAnalogHangTime(byte scanAnalogHangTime) {
		this.scanAnalogHangTime = scanAnalogHangTime;
	}

	@Override
	public byte getKeypadLockTime() {
		return keypadLockTime;
	}

	@Override
	public void setKeypadLockTime(byte keypadLockTime) {
		this.keypadLockTime = keypadLockTime;
	}

	@Override
	public byte getMode() {
		return mode;
	}

	@Override
	public void setMode(byte mode) {
		this.mode = mode;
	}

	@Override
	public int getPowerOnPIN() {
		return powerOnPIN;
	}

	@Override
	public void setPowerOnPIN(int powerOnPIN) {
		this.powerOnPIN = powerOnPIN;
	}

	@Override
	public int getRadioProgPIN() {
		return radioProgPIN;
	}

	@Override
	public void setRadioProgPIN(int radioProgPIN) {
		this.radioProgPIN = radioProgPIN;
	}

	@Override
	public String getPcProgPassword() {
		return "@todo"; // pcProgPassword;
	}

	@Override
	public void setPcProgPassword(String pcProgPassword) {
		this.pcProgPassword = "@todo"; // pcProgPassword;
	}

	@Override
	public String getRadioName() {
		return radioName;
	}

	@Override
	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}

}
