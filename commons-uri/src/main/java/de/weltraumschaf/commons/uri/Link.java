/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package de.weltraumschaf.commons.uri;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.namespace.QName;

/**
 * <p>
 * Class representing hypermedia links.
 * <p>
 * A hypermedia link may include additional parameters beyond its underlying URI. Parameters such as {@code rel} or
 * {@code type} provide additional meta-data. Links in responses can be <em>followed</em> by creating an
 * {@code javax.ws.rs.client.Invocation.Builder} or a {@code javax.ws.rs.client.WebTarget}.</p>
 *
 * <p>
 * The methods {@code #toString()} and {@code #valueOf} can be used to serialize and de-serialize a link into a link
 * header (RFC 5988).</p>
 *
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 * @since 2.0
 */
abstract class Link {

    /**
     * Title link param from RFC 5988.
     */
    public static final String TITLE = "title";

    /**
     * Rel link param from RFC 5988.
     */
    public static final String REL = "rel";

    /**
     * Type link param from RFC 5988.
     */
    public static final String TYPE = "type";

    /**
     * Returns the underlying URI associated with this link.
     *
     * @return underlying URI.
     */
    public abstract URI getUri();

    /**
     * Convenience method that returns a {@code javax.ws.rs.core.UriBuilder} initialized with this link's underlying
     * URI.
     *
     * @return BaseUriBuilder initialized using underlying URI.
     */
    public abstract BaseUriBuilder getUriBuilder();

    /**
     * Returns the value associated with the link {@code rel} param, or {@code null} if this param is not specified.
     *
     * @return relation types as string or {@code null}.
     */
    public abstract String getRel();

    /**
     * Returns the value associated with the link {@code rel} param as a list of strings or the empty list if
     * {@code rel} is not defined.
     *
     * @return relation types as list of strings or empty list.
     */
    public abstract List<String> getRels();

    /**
     * Returns the value associated with the link {@code title} param, or {@code null} if this param is not specified.
     *
     * @return value of title parameter or {@code null}.
     */
    public abstract String getTitle();

    /**
     * Returns the value associated with the link {@code type} param, or {@code null} if this param is not specified.
     *
     * @return value of type parameter or {@code null}.
     */
    public abstract String getType();

    /**
     * Returns an immutable map that includes all the link parameters defined on this link. If defined, this map will
     * include entries for {@code rel}, {@code title} and {@code type}.
     *
     * @return immutable map of link parameters.
     */
    public abstract Map<String, String> getParams();

    /**
     * {@inheritDoc}
     *
     * Returns a string representation as a link header (RFC 5988). All link params are serialized as link-param="value"
     * where value is a quoted-string. For example,
     *
     * {@literal <http://foo.bar/employee/john>; title="employee"; rel="manager friend"}
     */
    @Override
    public abstract String toString();

    /**
     * Builder class for hypermedia links.
     *
     * @see Link
     * @since 2.0
     */
    public interface Builder {

        /**
         * Initialize builder using another link. Sets underlying URI and copies all parameters.
         *
         * @param link other link from which to initialize.
         * @return the updated builder.
         */
        Builder link(Link link);

        /**
         * Initialize builder using another link represented as a string. Uses simple parser to convert string
         * representation into a link.
         * <pre>{@code
         * link ::= '<' uri '>' (';' link-param)*
         * link-param ::= name '=' quoted-string
         * }</pre>
         *
         * See <a href="http://tools.ietf.org/html/rfc5988">RFC 5988</a> for more information.
         *
         * @param link other link in string representation.
         * @return the updated builder.
         * @throws IllegalArgumentException if string representation of URI is invalid.
         */
        Builder link(String link);

        /**
         * Set underlying URI template for the link being constructed.
         *
         * @param uri underlying URI for link
         * @return the updated builder.
         */
        Builder uri(URI uri);

        /**
         * Set underlying string representing URI template for the link being constructed.
         *
         * @param uri underlying URI for link.
         * @return the updated builder.
         * @throws IllegalArgumentException if string representation of URI is invalid.
         */
        Builder uri(String uri);

        /**
         * Set the base URI for resolution of relative URIs. If the underlying URI is already absolute, the base URI is
         * ignored.
         *
         * @param uri base URI for relative links.
         * @return the updated builder.
         */
        Builder baseUri(URI uri);

        /**
         * Set the base URI as a string for resolution of relative URIs. If the underlying URI is already absolute, the
         * base URI is ignored.
         *
         * @param uri base URI for relative links.
         * @return the updated builder.
         * @throws IllegalArgumentException if string representation of URI is invalid.
         */
        Builder baseUri(String uri);

        /**
         * Set underlying URI builder representing the URI template for the link being constructed.
         *
         * @param uriBuilder underlying URI builder.
         * @return the updated builder.
         */
        Builder uriBuilder(BaseUriBuilder uriBuilder);

        /**
         * Convenience method to set a link relation. More than one {@code rel} value can be specified by using one or
         * more whitespace characters as delimiters according to RFC 5988. The effect of calling this method is
         * cumulative; relations are appended using a single space character as separator.
         *
         * @param rel relation name.
         * @return the updated builder.
         * @throws IllegalArgumentException if the name is {@code null}.
         */
        Builder rel(String rel);

        /**
         * Convenience method to set a {@code title} on this link.
         *
         * @param title title parameter of this link.
         * @return the updated builder.
         * @throws IllegalArgumentException if the title is {@code null}.
         */
        Builder title(String title);

        /**
         * Convenience method to set a {@code type} on this link.
         *
         * @param type type parameter of this link.
         * @return the updated builder.
         * @throws IllegalArgumentException if the type is {@code null}.
         */
        Builder type(String type);

        /**
         * Set an arbitrary parameter on this link. Note that link parameters are those defined in RFC 5988 and should
         * not be confused with URI parameters which can be specified when calling {@code #build(Object...)}.
         *
         * @param name the name of the parameter.
         * @param value the value set for the parameter.
         * @return the updated builder.
         * @throws IllegalArgumentException if either the name or value are {@code null}.
         */
        Builder param(String name, String value);

        /**
         * Finish building this link using the supplied values as URI parameters.
         *
         * The state of the builder is unaffected; this method may be called multiple times on the same builder
         * instance.
         *
         * @param values parameters used to build underlying URI.
         * @return newly built link.
         * @throws IllegalArgumentException if there are any URI template parameters without a supplied value, or if a
         * value is {@code null}.
         * @throws UriBuilderException if a URI cannot be constructed based on the current state of the underlying URI
         * builder.
         */
        Link build(Object... values);

        /**
         * Finish building this link using the supplied values as URI parameters and relativize the result with respect
         * to the supplied URI.
         *
         * If the underlying link is already relative or if it is absolute but does not share a prefix with the supplied
         * URI, this method is equivalent to calling {@code  Link.Builder#build(java.lang.Object[])}. Note that a base
         * URI can be set on a relative link using {@code  Link.Builder#baseUri(java.net.URI)}. The state of the builder
         * is unaffected; this method may be called multiple times on the same builder instance.
         *
         * @param uri URI used for relativization.
         * @param values parameters used to build underlying URI.
         * @return newly built link.
         * @throws IllegalArgumentException if there are any URI template parameters without a supplied value, or if a
         * value is {@code null}.
         * @throws UriBuilderException if a URI cannot be constructed based on the current state of the underlying URI
         * builder.
         * @see #baseUri(java.lang.String)
         * @see #baseUri(java.net.URI)
         */
        Link buildRelativized(URI uri, Object... values);
    }

    /**
     * Value type for {@code javax.ws.rs.core.Link} that can be marshalled and unmarshalled by JAXB.
     *
     * @since 2.0
     */
    public static class JaxbLink {

        private URI uri;
        private Map<QName, Object> params;

        /**
         * Default constructor needed during unmarshalling.
         */
        public JaxbLink() {
        }

        /**
         * Construct an instance from a URI and no parameters.
         *
         * @param uri underlying URI.
         */
        public JaxbLink(URI uri) {
            this.uri = uri;
        }

        /**
         * Construct an instance from a URI and some parameters.
         *
         * @param uri underlying URI.
         * @param params parameters of this link.
         */
        public JaxbLink(URI uri, Map<QName, Object> params) {
            this.uri = uri;
            this.params = params;
        }

        /**
         * Get the underlying URI for this link.
         *
         * @return underlying URI.
         */
        @XmlAttribute(name = "href")
        public URI getUri() {
            return uri;
        }

        /**
         * Get the parameter map for this link.
         *
         * @return parameter map.
         */
        @XmlAnyAttribute
        public Map<QName, Object> getParams() {
            if (params == null) {
                params = new HashMap<>();
            }
            return params;
        }
    }

}
