package com.lra.graduation;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHandler;
import org.jnetpcap.PcapIf;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/11/5 15:37
 * @Version V1.0
 **/
public class XVC {
    public static void main(final String[] args) {
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder();     // For any error msgs

        /********************************************
         * First get a list of devices on this system
         ********************************************/
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf
                    .toString());
            return;
        }

        System.out.println("Network devices found:");

        int i = 0;
        for (PcapIf device : alldevs) {
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(), device
                    .getDescription());
        }

        PcapIf device = alldevs.get(2); // We know we have atleast 1 device
        System.out.printf("\nChoosing '%s' on your behalf:\n", device
                .getDescription());

        /***************************************
         * Second we open up the selected device
         ***************************************/
        int snaplen = 64 * 1024;           // Capture all packets, no trucation
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        int timeout = 10 * 1000;           // 10 seconds in millis
        Pcap pcap = Pcap
                .openLive(device.getName(), snaplen, flags, timeout, errbuf);

        if (pcap == null) {
            System.err.printf("Error while opening device for capture: "
                    + errbuf.toString());
            return;
        }

        /**********************************************************************
         * Third we create a packet hander which will be dispatched to from the
         * libpcap loop.
         **********************************************************************/
        PcapHandler<String> printSummaryHandler = new PcapHandler<String>() {

            public void nextPacket(String user, long seconds, int useconds,
                                   int caplen, int len, ByteBuffer buffer) {
                Date timestamp = new Date(seconds * 1000 + useconds/1000); // In millis

                System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
                        timestamp.toString(), // timestamp to 1 ms accuracy
                        caplen, // Length actually captured
                        len,    // Original length of the packet
                        user    // User supplied object
                );

                byte[] byten = new byte[buffer.limit()]; // 可用的字节数量
                buffer.get(byten, buffer.position(), buffer.limit()); // 得到目前为止缓冲区所有的数据

                try {
                    System.out.println(new String(byten,"gbk"));
                    System.out.println(new String(byten,"ISO-8859-1"));
                    System.out.println(new String(byten,"ASCII"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        };

        /************************************************************
         * Fourth we enter the loop and tell it to capture 10 packets
         ************************************************************/
        pcap.loop(Integer.MAX_VALUE, printSummaryHandler, "jNetPcap rocks!");

        /*
         * Last thing to do is close the pcap handle
         */
        pcap.close();
    }
}
