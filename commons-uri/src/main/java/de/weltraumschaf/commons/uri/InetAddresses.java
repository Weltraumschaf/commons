/*
 * Copyright (C) 2008 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.weltraumschaf.commons.uri;

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Static utility methods pertaining to {@code  InetAddress} instances.
 *
 * <p>
 * <b>Important note:</b> Unlike {@code InetAddress.getByName()}, the methods of this class never cause DNS services to
 * be accessed. For this reason, you should prefer these methods as much as possible over their JDK equivalents whenever
 * you are expecting to handle only IP address string literals -- there is no blocking DNS penalty for a malformed
 * string.
 *
 * <p>
 * When dealing with {@code  Inet4Address} and {@code  Inet6Address} objects as byte arrays (vis.
 * {@code InetAddress.getAddress()}) they are 4 and 16 bytes in length, respectively, and represent the address in
 * network byte order.
 *
 * <p>
 * Examples of IP addresses and their byte representations:
 * <ul>
 * <li>The IPv4 loopback address, {@code "127.0.0.1"}. {@code 7f 00 00 01}</li>
 *
 * <li>The IPv6 loopback address, {@code "::1"}. {@code 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01}</li>
 *
 * <li>From the IPv6 reserved documentation prefix ({@code 2001:db8::/32}),
 *     {@code "2001:db8::1"}. {@code 20 01 0d b8 00 00 00 00 00 00 00 00 00 00 00 01}</li>
 *
 * <li>An IPv6 "IPv4 compatible" (or "compat") address, {@code "::192.168.0.1"}.
 * {@code 00 00 00 00 00 00 00 00 00 00 00 00 c0 a8 00 01}</li>
 *
 * <li>An IPv6 "IPv4 mapped" address, {@code "::ffff:192.168.0.1"}.
 * {@code 00 00 00 00 00 00 00 00 00 00 ff ff c0 a8 00 01}</li>
 * </ul>
 *
 * <p>
 * A few notes about IPv6 "IPv4 mapped" addresses and their observed use in Java.
 * </p>
 * <p>
 * "IPv4 mapped" addresses were originally a representation of IPv4 addresses for use on an IPv6 socket that could
 * receive both IPv4 and IPv6 connections (by disabling the {@code IPV6_V6ONLY} socket option on an IPv6 socket). Yes,
 * it's confusing. Nevertheless, these "mapped" addresses were never supposed to be seen on the wire. That assumption
 * was dropped, some say mistakenly, in later RFCs with the apparent aim of making IPv4-to-IPv6 transition simpler.
 * </p>
 * <p>
 * Technically one <i>can</i> create a 128bit IPv6 address with the wire format of a "mapped" address, as shown above,
 * and transmit it in an IPv6 packet header. However, Java's InetAddress creation methods appear to adhere doggedly to
 * the original intent of the "mapped" address: all "mapped" addresses return {@code  Inet4Address} objects.
 * </p>
 * <p>
 * For added safety, it is common for IPv6 network operators to filter all packets where either the source or
 * destination address appears to be a "compat" or "mapped" address. Filtering suggestions usually recommend discarding
 * any packets with source or destination addresses in the invalid range {@code ::/3}, which includes both of these
 * bizarre address formats. For more information on "bogons", including lists of IPv6 bogon space, see:
 * </p>
 *
 * <ul>
 * <li><a target="_parent"
 *        href="http://en.wikipedia.org/wiki/Bogon_filtering"
 *       >http://en.wikipedia.org/wiki/Bogon_filtering</a>
 * <li><a target="_parent"
 *        href="http://www.cymru.com/Bogons/ipv6.txt"
 *       >http://www.cymru.com/Bogons/ipv6.txt</a>
 * <li><a target="_parent"
 *        href="http://www.cymru.com/Bogons/v6bogon.html"
 *       >http://www.cymru.com/Bogons/v6bogon.html</a>
 * <li><a target="_parent"
 *        href="http://www.space.net/~gert/RIPE/ipv6-filters.html"
 *       >http://www.space.net/~gert/RIPE/ipv6-filters.html</a>
 * </ul>
 *
 * @author Erik Kline
 * @since 5.0
 */
final class InetAddresses {

    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;
    private static final Inet4Address LOOPBACK4 = (Inet4Address) forString("127.0.0.1");
    private static final Inet4Address ANY4 = (Inet4Address) forString("0.0.0.0");

    private InetAddresses() {
    }

    /**
     * Returns an {@code  Inet4Address}, given a byte array representation of the IPv4 address.
     *
     * @param bytes byte array representing an IPv4 address (should be of length 4)
     * @return {@code  Inet4Address} corresponding to the supplied byte array
     * @throws IllegalArgumentException if a valid {@code  Inet4Address} can not be created
     */
    private static Inet4Address getInet4Address(byte[] bytes) {
        Validate.isTrue(bytes.length == 4,
            String.format("Byte array has invalid length for an IPv4 address: %s != 4.",
                bytes.length));

        // Given a 4-byte array, this cast should always succeed.
        return (Inet4Address) bytesToInetAddress(bytes);
    }

    /**
     * Returns the {@code  InetAddress} having the given string representation.
     *
     * <p>
     * This deliberately avoids all nameservice lookups (e.g. no DNS).
     *
     * @param ipString {@code String} containing an IPv4 or IPv6 string literal, e.g. {@code "192.168.0.1"} or
     * {@code "2001:db8::1"}
     * @return {@code  InetAddress} representing the argument
     * @throws IllegalArgumentException if the argument is not a valid IP string literal
     */
    static InetAddress forString(String ipString) {
        byte[] addr = ipStringToBytes(ipString);

        // The argument was malformed, i.e. not an IP string literal.
        if (addr == null) {
            throw new IllegalArgumentException(
                String.format("'%s' is not an IP string literal.", ipString));
        }

        return bytesToInetAddress(addr);
    }

    /**
     * Returns {@code true} if the supplied string is a valid IP string literal, {@code false} otherwise.
     *
     * @param ipString {@code String} to evaluated as an IP string literal
     * @return {@code true} if the argument is a valid IP string literal
     */
    static boolean isInetAddress(String ipString) {
        return ipStringToBytes(ipString) != null;
    }

    private static byte[] ipStringToBytes(String ipString) {
        // Make a first pass to categorize the characters in this string.
        boolean hasColon = false;
        boolean hasDot = false;
        for (int i = 0; i < ipString.length(); i++) {
            char c = ipString.charAt(i);
            if (c == '.') {
                hasDot = true;
            } else if (c == ':') {
                if (hasDot) {
                    return null;  // Colons must not appear after dots.
                }
                hasColon = true;
            } else if (Character.digit(c, 16) == -1) {
                return null;  // Everything else must be a decimal or hex digit.
            }
        }

        // Now decide which address family to parse.
        if (hasColon) {
            if (hasDot) {
                ipString = convertDottedQuadToHex(ipString);
                if (ipString == null) {
                    return null;
                }
            }
            return textToNumericFormatV6(ipString);
        } else if (hasDot) {
            return textToNumericFormatV4(ipString);
        }
        return null;
    }

    private static byte[] textToNumericFormatV4(String ipString) {
        String[] address = ipString.split("\\.", IPV4_PART_COUNT + 1);
        if (address.length != IPV4_PART_COUNT) {
            return null;
        }

        byte[] bytes = new byte[IPV4_PART_COUNT];
        try {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = parseOctet(address[i]);
            }
        } catch (NumberFormatException ex) {
            return null;
        }

        return bytes;
    }

    private static byte[] textToNumericFormatV6(String ipString) {
        // An address can have [2..8] colons, and N colons make N+1 parts.
        String[] parts = ipString.split(":", IPV6_PART_COUNT + 2);
        if (parts.length < 3 || parts.length > IPV6_PART_COUNT + 1) {
            return null;
        }

    // Disregarding the endpoints, find "::" with nothing in between.
        // This indicates that a run of zeroes has been skipped.
        int skipIndex = -1;
        for (int i = 1; i < parts.length - 1; i++) {
            if (parts[i].length() == 0) {
                if (skipIndex >= 0) {
                    return null;  // Can't have more than one ::
                }
                skipIndex = i;
            }
        }

        int partsHi;  // Number of parts to copy from above/before the "::"
        int partsLo;  // Number of parts to copy from below/after the "::"
        if (skipIndex >= 0) {
            // If we found a "::", then check if it also covers the endpoints.
            partsHi = skipIndex;
            partsLo = parts.length - skipIndex - 1;
            if (parts[0].length() == 0 && --partsHi != 0) {
                return null;  // ^: requires ^::
            }
            if (parts[parts.length - 1].length() == 0 && --partsLo != 0) {
                return null;  // :$ requires ::$
            }
        } else {
      // Otherwise, allocate the entire address to partsHi.  The endpoints
            // could still be empty, but parseHextet() will check for that.
            partsHi = parts.length;
            partsLo = 0;
        }

    // If we found a ::, then we must have skipped at least one part.
        // Otherwise, we must have exactly the right number of parts.
        int partsSkipped = IPV6_PART_COUNT - (partsHi + partsLo);
        if (!(skipIndex >= 0 ? partsSkipped >= 1 : partsSkipped == 0)) {
            return null;
        }

        // Now parse the hextets into a byte array.
        ByteBuffer rawBytes = ByteBuffer.allocate(2 * IPV6_PART_COUNT);
        try {
            for (int i = 0; i < partsHi; i++) {
                rawBytes.putShort(parseHextet(parts[i]));
            }
            for (int i = 0; i < partsSkipped; i++) {
                rawBytes.putShort((short) 0);
            }
            for (int i = partsLo; i > 0; i--) {
                rawBytes.putShort(parseHextet(parts[parts.length - i]));
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return rawBytes.array();
    }

    private static String convertDottedQuadToHex(String ipString) {
        int lastColon = ipString.lastIndexOf(':');
        String initialPart = ipString.substring(0, lastColon + 1);
        String dottedQuad = ipString.substring(lastColon + 1);
        byte[] quad = textToNumericFormatV4(dottedQuad);
        if (quad == null) {
            return null;
        }
        String penultimate = Integer.toHexString(((quad[0] & 0xff) << 8) | (quad[1] & 0xff));
        String ultimate = Integer.toHexString(((quad[2] & 0xff) << 8) | (quad[3] & 0xff));
        return initialPart + penultimate + ":" + ultimate;
    }

    private static byte parseOctet(String ipPart) {
        // Note: we already verified that this string contains only hex digits.
        int octet = Integer.parseInt(ipPart);
    // Disallow leading zeroes, because no clear standard exists on
        // whether these should be interpreted as decimal or octal.
        if (octet > 255 || (ipPart.startsWith("0") && ipPart.length() > 1)) {
            throw new NumberFormatException();
        }
        return (byte) octet;
    }

    private static short parseHextet(String ipPart) {
        // Note: we already verified that this string contains only hex digits.
        int hextet = Integer.parseInt(ipPart, 16);
        if (hextet > 0xffff) {
            throw new NumberFormatException();
        }
        return (short) hextet;
    }

    /**
     * Convert a byte array into an InetAddress.
     *
     * {@code  InetAddress#getByAddress} is documented as throwing a checked exception "if IP address if of illegal
     * length." We replace it with an unchecked exception, for use by callers who already know that addr is an array of
     * length 4 or 16.
     *
     * @param addr the raw 4-byte or 16-byte IP address in big-endian order
     * @return an InetAddress object created from the raw IP address
     */
    private static InetAddress bytesToInetAddress(byte[] addr) {
        try {
            return InetAddress.getByAddress(addr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Identify and mark the longest run of zeroes in an IPv6 address.
     *
     * <p>
     * Only runs of two or more hextets are considered. In case of a tie, the leftmost run wins. If a qualifying run is
     * found, its hextets are replaced by the sentinel value -1.
     *
     * @param hextets {@code int[]} mutable array of eight 16-bit hextets
     */
    private static void compressLongestRunOfZeroes(int[] hextets) {
        int bestRunStart = -1;
        int bestRunLength = -1;
        int runStart = -1;
        for (int i = 0; i < hextets.length + 1; i++) {
            if (i < hextets.length && hextets[i] == 0) {
                if (runStart < 0) {
                    runStart = i;
                }
            } else if (runStart >= 0) {
                int runLength = i - runStart;
                if (runLength > bestRunLength) {
                    bestRunStart = runStart;
                    bestRunLength = runLength;
                }
                runStart = -1;
            }
        }
        if (bestRunLength >= 2) {
            Arrays.fill(hextets, bestRunStart, bestRunStart + bestRunLength, -1);
        }
    }

    /**
     * Convert a list of hextets into a human-readable IPv6 address.
     *
     * <p>
     * In order for "::" compression to work, the input should contain negative sentinel values in place of the elided
     * zeroes.
     *
     * @param hextets {@code int[]} array of eight 16-bit hextets, or -1s
     * @return TODO
     */
    private static String hextetsToIPv6String(int[] hextets) {
        /*
         * While scanning the array, handle these state transitions:
         *   start->num => "num"     start->gap => "::"
         *   num->num   => ":num"    num->gap   => "::"
         *   gap->num   => "num"     gap->gap   => ""
         */
        StringBuilder buf = new StringBuilder(39);
        boolean lastWasNumber = false;
        for (int i = 0; i < hextets.length; i++) {
            boolean thisIsNumber = hextets[i] >= 0;
            if (thisIsNumber) {
                if (lastWasNumber) {
                    buf.append(':');
                }
                buf.append(Integer.toHexString(hextets[i]));
            } else {
                if (i == 0 || lastWasNumber) {
                    buf.append("::");
                }
            }
            lastWasNumber = thisIsNumber;
        }
        return buf.toString();
    }

    /**
     * Returns an InetAddress representing the literal IPv4 or IPv6 host portion of a URL, encoded in the format
     * specified by RFC 3986 section 3.2.2.
     *
     * <p>
     * This function is similar to {@code  InetAddresses#forString(String)}, however, it requires that IPv6 addresses are
     * surrounded by square brackets.
     * </p>
     * <p>
     * This function is the inverse of {@code InetAddresses#toUriString(java.net.InetAddress)}.</p>
     *
     * @param hostAddr A RFC 3986 section 3.2.2 encoded IPv4 or IPv6 address
     * @return an InetAddress representing the address in {@code hostAddr}
     * @throws IllegalArgumentException if {@code hostAddr} is not a valid IPv4 address, or IPv6 address surrounded by
     * square brackets
     */
    static InetAddress forUriString(String hostAddr) {
        Validate.notNull(hostAddr);

        // Decide if this should be an IPv6 or IPv4 address.
        String ipString;
        int expectBytes;
        if (hostAddr.startsWith("[") && hostAddr.endsWith("]")) {
            ipString = hostAddr.substring(1, hostAddr.length() - 1);
            expectBytes = 16;
        } else {
            ipString = hostAddr;
            expectBytes = 4;
        }

        // Parse the address, and make sure the length/version is correct.
        byte[] addr = ipStringToBytes(ipString);
        if (addr == null || addr.length != expectBytes) {
            throw new IllegalArgumentException(
                String.format("Not a valid URI IP literal: '%s'", hostAddr));
        }

        return bytesToInetAddress(addr);
    }

    /**
     * Returns {@code true} if the supplied string is a valid URI IP string literal, {@code false} otherwise.
     *
     * @param ipString {@code String} to evaluated as an IP URI host string literal
     * @return {@code true} if the argument is a valid IP URI host
     */
    static boolean isUriInetAddress(String ipString) {
        try {
            forUriString(ipString);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Evaluates whether the argument is an IPv6 "compat" address.
     *
     * <p>
     * An "IPv4 compatible", or "compat", address is one with 96 leading bits of zero, with the remaining 32 bits
     * interpreted as an IPv4 address. These are conventionally represented in string literals as
     * {@code "::192.168.0.1"}, though {@code "::c0a8:1"} is also considered an IPv4 compatible address (and equivalent
     * to {@code "::192.168.0.1"}).
     *
     * <p>
     * For more on IPv4 compatible addresses see section 2.5.5.1 of
     * <a target="_parent"
     *    href="http://tools.ietf.org/html/rfc4291#section-2.5.5.1"
     *    >http://tools.ietf.org/html/rfc4291</a>
     *
     * <p>
     * NOTE: This method is different from {@code  Inet6Address#isIPv4CompatibleAddress} in that it more correctly
     * classifies {@code "::"} and {@code "::1"} as proper IPv6 addresses (which they are), NOT IPv4 compatible
     * addresses (which they are generally NOT considered to be).
     *
     * @param ip {@code  Inet6Address} to be examined for embedded IPv4 compatible address format
     * @return {@code true} if the argument is a valid "compat" address
     */
    static boolean isCompatIPv4Address(Inet6Address ip) {
        if (!ip.isIPv4CompatibleAddress()) {
            return false;
        }

        byte[] bytes = ip.getAddress();
        if ((bytes[12] == 0) && (bytes[13] == 0) && (bytes[14] == 0)
            && ((bytes[15] == 0) || (bytes[15] == 1))) {
            return false;
        }

        return true;
    }

    /**
     * Evaluates whether the argument is a 6to4 address.
     *
     * <p>
     * 6to4 addresses begin with the {@code "2002::/16"} prefix. The next 32 bits are the IPv4 address of the host to
     * which IPv6-in-IPv4 tunneled packets should be routed.
     *
     * <p>
     * For more on 6to4 addresses see section 2 of
     * <a target="_parent" href="http://tools.ietf.org/html/rfc3056#section-2"
     *    >http://tools.ietf.org/html/rfc3056</a>
     *
     * @param ip {@code  Inet6Address} to be examined for 6to4 address format
     * @return {@code true} if the argument is a 6to4 address
     */
    static boolean is6to4Address(Inet6Address ip) {
        byte[] bytes = ip.getAddress();
        return (bytes[0] == (byte) 0x20) && (bytes[1] == (byte) 0x02);
    }

    /**
     * A simple immutable data class to encapsulate the information to be found in a Teredo address.
     *
     * <p>
     * All of the fields in this class are encoded in various portions of the IPv6 address as part of the protocol. More
     * protocols details can be found at:
     * <a target="_parent" href="http://en.wikipedia.org/wiki/Teredo_tunneling"
     *    >http://en.wikipedia.org/wiki/Teredo_tunneling</a>.
     *
     * <p>
     * The RFC can be found here:
     * <a target="_parent" href="http://tools.ietf.org/html/rfc4380"
     *    >http://tools.ietf.org/html/rfc4380</a>.
     *
     * @since 5.0
     */
    static final class TeredoInfo {

        private final Inet4Address server;
        private final Inet4Address client;
        private final int port;
        private final int flags;

        /**
         * Constructs a TeredoInfo instance.
         *
         * <p>
         * Both server and client can be {@code null}, in which case the value {@code "0.0.0.0"} will be assumed.
         *
         * @throws IllegalArgumentException if either of the {@code port} or the {@code flags} arguments are out of
         * range of an unsigned short
         *
         * @param server TODO
         * @param client TODO
         * @param port TODO
         * @param flags TODO
         */
        // TODO: why is this ?
        TeredoInfo(Inet4Address server, Inet4Address client, int port, int flags) {
            Validate.isTrue((port >= 0) && (port <= 0xffff),
                String.format("port '%s' is out of range (0 <= port <= 0xffff)", port));
            Validate.isTrue((flags >= 0) && (flags <= 0xffff),
                String.format("flags '%s' is out of range (0 <= flags <= 0xffff)", flags));

            this.server = Objects.firstNonNull(server, ANY4);
            this.client = Objects.firstNonNull(client, ANY4);
            this.port = port;
            this.flags = flags;
        }

        Inet4Address getServer() {
            return server;
        }

        Inet4Address getClient() {
            return client;
        }

        int getPort() {
            return port;
        }

        int getFlags() {
            return flags;
        }
    }

    /**
     * Evaluates whether the argument is a Teredo address.
     *
     * <p>
     * Teredo addresses begin with the {@code "2001::/32"} prefix.
     *
     * @param ip {@code  Inet6Address} to be examined for Teredo address format
     * @return {@code true} if the argument is a Teredo address
     */
    static boolean isTeredoAddress(Inet6Address ip) {
        byte[] bytes = ip.getAddress();
        return (bytes[0] == (byte) 0x20) && (bytes[1] == (byte) 0x01)
            && (bytes[2] == 0) && (bytes[3] == 0);
    }

    /**
     * Evaluates whether the argument is an ISATAP address.
     *
     * <p>
     * From RFC 5214: "ISATAP interface identifiers are constructed in Modified EUI-64 format [...] by concatenating the
     * 24-bit IANA OUI (00-00-5E), the 8-bit hexadecimal value 0xFE, and a 32-bit IPv4 address in network byte order
     * [...]"
     *
     * <p>
     * For more on ISATAP addresses see section 6.1 of
     * <a target="_parent" href="http://tools.ietf.org/html/rfc5214#section-6.1"
     *    >http://tools.ietf.org/html/rfc5214</a>
     *
     * @param ip {@code  Inet6Address} to be examined for ISATAP address format
     * @return {@code true} if the argument is an ISATAP address
     */
    static boolean isIsatapAddress(Inet6Address ip) {

    // If it's a Teredo address with the right port (41217, or 0xa101)
        // which would be encoded as 0x5efe then it can't be an ISATAP address.
        if (isTeredoAddress(ip)) {
            return false;
        }

        byte[] bytes = ip.getAddress();

        if ((bytes[8] | (byte) 0x03) != (byte) 0x03) {

      // Verify that high byte of the 64 bit identifier is zero, modulo
            // the U/L and G bits, with which we are not concerned.
            return false;
        }

        return (bytes[9] == (byte) 0x00) && (bytes[10] == (byte) 0x5e)
            && (bytes[11] == (byte) 0xfe);
    }

    /**
     * Examines the Inet6Address to determine if it is an IPv6 address of one of the specified address types that
     * contain an embedded IPv4 address.
     *
     * <p>
     * NOTE: ISATAP addresses are explicitly excluded from this method due to their trivial spoofability. With other
     * transition addresses spoofing involves (at least) infection of one's BGP routing table.
     *
     * @param ip {@code  Inet6Address} to be examined for embedded IPv4 client address
     * @return {@code true} if there is an embedded IPv4 client address
     * @since 7.0
     */
    static boolean hasEmbeddedIPv4ClientAddress(Inet6Address ip) {
        return isCompatIPv4Address(ip) || is6to4Address(ip) || isTeredoAddress(ip);
    }

    /**
     * Evaluates whether the argument is an "IPv4 mapped" IPv6 address.
     *
     * <p>
     * An "IPv4 mapped" address is anything in the range ::ffff:0:0/96 (sometimes written as ::ffff:0.0.0.0/96), with
     * the last 32 bits interpreted as an IPv4 address.
     *
     * <p>
     * For more on IPv4 mapped addresses see section 2.5.5.2 of
     * <a target="_parent"
     *    href="http://tools.ietf.org/html/rfc4291#section-2.5.5.2"
     *    >http://tools.ietf.org/html/rfc4291</a>
     *
     * <p>
     * Note: This method takes a {@code String} argument because {@code  InetAddress} automatically collapses mapped
     * addresses to IPv4. (It is actually possible to avoid this using one of the obscure {@code  Inet6Address} methods,
     * but it would be unwise to depend on such a poorly-documented feature.)
     *
     * @param ipString {@code String} to be examined for embedded IPv4-mapped IPv6 address format
     * @return {@code true} if the argument is a valid "mapped" address
     * @since 10.0
     */
    static boolean isMappedIPv4Address(String ipString) {
        byte[] bytes = ipStringToBytes(ipString);
        if (bytes != null && bytes.length == 16) {
            for (int i = 0; i < 10; i++) {
                if (bytes[i] != 0) {
                    return false;
                }
            }
            for (int i = 10; i < 12; i++) {
                if (bytes[i] != (byte) 0xff) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns an address from a <b>little-endian ordered</b> byte array (the opposite of what
     * {@code  InetAddress#getByAddress} expects).
     *
     * <p>
     * IPv4 address byte array must be 4 bytes long and IPv6 byte array must be 16 bytes long.
     *
     * @param addr the raw IP address in little-endian byte order
     * @return an InetAddress object created from the raw IP address
     * @throws UnknownHostException if IP address is of illegal length
     */
    static InetAddress fromLittleEndianByteArray(byte[] addr) throws UnknownHostException {
        byte[] reversed = new byte[addr.length];
        for (int i = 0; i < addr.length; i++) {
            reversed[i] = addr[addr.length - i - 1];
        }
        return InetAddress.getByAddress(reversed);
    }

    /**
     * Returns a new InetAddress that is one more than the passed in address. This method works for both IPv4 and IPv6
     * addresses.
     *
     * @param address the InetAddress to increment
     * @return a new InetAddress that is one more than the passed in address
     * @throws IllegalArgumentException if InetAddress is at the end of its range
     * @since 10.0
     */
    static InetAddress increment(InetAddress address) {
        byte[] addr = address.getAddress();
        int i = addr.length - 1;
        while (i >= 0 && addr[i] == (byte) 0xff) {
            addr[i] = 0;
            i--;
        }

        Validate.isTrue(i >= 0, String.format("Incrementing %s would wrap.", address));

        addr[i]++;
        return bytesToInetAddress(addr);
    }

    /**
     * Returns true if the InetAddress is either 255.255.255.255 for IPv4 or ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff for
     * IPv6.
     *
     * @return true if the InetAddress is either 255.255.255.255 for IPv4 or ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff for
     * IPv6
     * @since 10.0
     * @param address TODO
     */
    static boolean isMaximum(InetAddress address) {
        byte[] addr = address.getAddress();
        for (int i = 0; i < addr.length; i++) {
            if (addr[i] != (byte) 0xff) {
                return false;
            }
        }
        return true;
    }

}
