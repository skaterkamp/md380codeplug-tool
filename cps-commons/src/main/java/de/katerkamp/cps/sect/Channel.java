/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.sect;

import de.katerkamp.cps.ds.channel.ChannelMode;
import de.katerkamp.cps.ds.channel.RefFrequency;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public interface Channel {


	@XmlElement
	@NotNull
	@Size(min=1)
	public String getName();

	public void setName(String key);

	// Digital/Analog Data

	@XmlElement
	public ChannelMode getMode();

	public void setMode(ChannelMode channelMode);

	@XmlElement
	public RefFrequency getRxRefFrequency();

	public void setRxRefFrequency(RefFrequency refFrequency);

	@XmlElement
	public RefFrequency getTxRefFrequency();

	public void setTxRefFrequency(RefFrequency refFrequency);

	@XmlElement
	public int getContactMember();

	public void setContactMember(int contactMember);

	//boolean loneWorker;


	// Analog Data

	// Analog Data

}


/*
  LoneWorker            1 bit,   0 binary enum {0="off" 1="on"}
        Squelch               1 bit,   2 binary enum {0="tight" 1="normal"}
        Autoscan              1 bit,   3 binary enum {0="off" 1="on"}
        Bandwidth             1 bit,   4 binary enum {0="12.5" 1="25.0"}
        Colorcode             4 bit,   8 binary comment %F%10<=N<=15%f%0
        RepeaterSlot          2 bit,  12 binary enum {0b01="slot1" 0b10="slot2"}
        RxOnly                1 bit,  14 binary enum {0="off" 1="on"}
        AllowTalkaround       1 bit,  15 binary enum {0="off" 1="on"}
        DataCallConf          1 bit,  16 binary enum {0="off" 1="on"}
        PrivateCallConf       1 bit,  17 binary enum {0="off" 1="on"}
        Privacy               2 bit,  18 binary enum {0b00="none" 0b01="basic" 0b10="enhanced"}
        PrivacyNo             4 bit,  20 binary comment %F%10<=N<=7 if privacy is enhanced; 0<=N<=15 if basic%f%0
                validation %F%1if (rec->Privacy == 2 && rec->PrivacyNo > 7) REPORT ("out of range (with Privacy=enhanced, range is 0-7)");  %f%0
        DisplayPttId          1 bit,  24 binary enum {0="on" 1="off"} # (NOTE, on/off is reversed!)
        CompressedUdpHdr      1 bit,  25 binary enum {0="off" 1="on"}
        fixed                 1 bit,  26 value=1
        EmergencyAlarmAck     1 bit,  28 binary enum {0="off" 1="on"}
        AdmintCriteria        2 bit,  32 binary enum {0b00="always" 0b01="chFree" 0b10="CTCSS/DCS" 0b11="colorCode"}
        Power                 1 bit,  34 binary enum {0="low" 1="high"}
        Vox                   1 bit,  35 binary enum {0="off" 1="on"}
        QtReverse             1 bit,  36 binary enum {0="180" 1="120"}
        ReverseBurst          1 bit,  37 binary enum {0="off" 1="on"}
        fixed                 8 bit,  40 value=0xC3
        ContactName          16 bit,  48 bind DigitalContact enum {0="none"}  # LSB and MSB; 0=none otherwise position in users list (max 989 although lists reaches 1000?)
        Tot                   6 bit,  66 binary comment %F%10=infinite; otherwise time in seconds, s=15*N%f%0
        TotRekeyDelay         8 bit,  72 binary comment %F%1number of seconds%f%0
        EmergencySystem       6 bit,  82 binary  # nnnnnn 0=none otherwise 1<=n<=32 refers to entry in Digit Emergency System
        ScanList              8 bit,  88 bind ScanList enum {0="none"} # nnnnnnnn 0=none otherwise position in the scan list (1 to 250).
        GroupList             8 bit,  96 bind DigitalRxGroupList enum {0="none"} # nnnnnnnn 0=none otherwise position in the group list (1 to 250).
        fixed                 8 bit, 104 value=1
        Decode18              8 bit, 112 binary enum {0="off" 1="on"} # (available only if RX signaling system is not "off"); MSB is decode 8, LSB is decode 1
        fixed                 8 bit, 120 value = 0xff
        deletionMarker        8 bit, 128 value = 0xFF
        RxFrequency          32 bit, 128 BCD comment %F%1Frequency * 10 Hz (for example, 43098570 = 430.98570 MHz)%f%0  # BCD x67 x45 x23 x41 means 412.34567 MHz
        TxFrequency          32 bit, 160 BCD comment %F%1Frequency * 10 Hz (for example, 43098570 = 430.98570 MHz)%f%0  # BCD x67 x45 x23 x41 means 412.34567 MHz
        CtcssDcsDecode       16 bit, 192 BCDT format %F%1CTCSS/DCS tones - See description of BCDT%f%0 enum {    #

		}
 CtcssDcsEncode 
 TxSignalingSyst       3 bit, 237 binary  # 000=Off nnn=DTMF-n (1 <= n <= 4)
        RxSignalingSyst       3 bit, 229 binary  # 000=Off nnn=DTMF-n (1 <= n <= 4)

*/
