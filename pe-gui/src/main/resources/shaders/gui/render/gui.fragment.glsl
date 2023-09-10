// Per Vertex attibutes
in vec2 v_position;
in vec4 v_bounds;
in vec4 v_borders;
in vec4 v_backgroundColour;

// Uniforms
uniform bool u_hasTexture;
uniform sampler2D u_texture;
uniform bool u_hasDiscardMap;
uniform sampler2D u_discardMap;
uniform ivec2 u_resolution;
uniform vec4 u_radiuses;
uniform float u_opacity;
uniform uint u_borderColour;
uniform vec4 u_colourModifier;

// Output
out vec4 fragColour;

// Global variables
float g_radius;
float g_radiusAlpha = 1;

/**
* Returns whether the rectangle contains the position
*
* @param rectangle - the rectangle
* @param position  - the position
* @return true if the rectangle contains the position, false otherwise
*/
bool contains(vec4 rectangle, vec2 position) {
    return rectangle.x <= position.x && rectangle.y <= position.y
    && rectangle.x + rectangle.z >= position.x
    && rectangle.y + rectangle.w >= position.y;
}

/**
* Returns the normalized device coordinates relative to the given position
*
* @param v - 2D position on the scene, screen coordinates
* @return the normalized device coordinates
*/
vec2 toNdc(vec2 v) {
    return v / u_resolution;
}

/**
* Returns the normalized device coordinates relative to the given rectangle
*
* @param v - 2D rectangle on the scene, screen coordinates
* @return the normalized device coordinates
*/
vec4 toNdc(vec4 rectangle) {
    return rectangle / vec4(u_resolution, u_resolution);
}

/**
* Returns the position on the screen relative to the given position
*
* @param v - 2D position on the scene, normalized device coordinates
* @return the screen coordinates
*/
vec2 fromNdc(vec2 v) {
    return v * u_resolution;
}

/**
* Returns the position on the screen relative to the given rectangle
*
* @param v - 2D rectangle on the scene, normalized device coordinates
* @return the screen coordinates
*/
vec4 fromNdc(vec4 v) {
    return v * vec4(u_resolution, u_resolution);
}

/**
* Returns the position in the screen relative to the normalized position in the rectangle
*
* @param rectangle - the rectangle
* @param position  - the position
* @return position on screen
*/
vec2 mapInRectangle(vec4 rectangle, vec2 position) {
    return rectangle.xy + position * rectangle.zw;
}

/**
* Returns the center of the rectangle
*
* @param rectangle - the rectangle
* @return the center of the rectangle
*/
vec2 center(vec4 rectangle) {
    rectangle = fromNdc(v_bounds);
    return rectangle.xy + rectangle.zw * .5;
}

/**
* Returns radius that compatible with the given position
* The radius is increased for bounds greater than v_bounds
* This method is used in order to create rounded rectangles
*
* @param bounds   - the bounds
* @param position - the position
* @return the corresponding radius
*/
float getRadius(vec4 bounds, vec2 position) {
    vec2 center = center(bounds);
    if (position.x < center.x) {
        return (position.y < center.y ? u_radiuses.x : u_radiuses.y);
    } else {
        return (position.y > center.y ? u_radiuses.z : u_radiuses.w);
    }
}

/**
* Returns the center of the edge that compatible with the given position
* This method is used in order to create rounded rectangles
*
* @param bounds   - the bounds
* @param position - the position
* @return the corresponding radius
*/
vec2 edgeCenter(vec4 bounds, vec2 position) {
    position = fromNdc(position);
    bounds = fromNdc(bounds);
    float x = bounds.x;
    float y = bounds.y;
    float w = bounds.z;
    float h = bounds.w;

    float r = g_radius;
    vec2 center = center(bounds);

    vec2 edgeCenter = vec2(0, 0);
    edgeCenter.x = position.x < center.x ? x + r : x + w - r;
    edgeCenter.y = position.y < center.y ? y + r : y + h - r;
    return edgeCenter;
}

/**
* Returns whether this position of the fragment is inside the given bounds
* This method takes into account the possible rounded corners
*
* @param bounds - the bounds
* @return the corresponding radius
*/
bool isInside(vec4 bounds) {
    vec2 position = (mapInRectangle(fromNdc(v_bounds), v_position));

    vec4 boundsNdc = fromNdc(bounds);
    float r = g_radius;

    vec4 boundsVertical = boundsNdc + vec4(r, 0, -r * 2, 0);
    vec4 boundsHorizontal = boundsNdc + vec4(0, r, 0, -r * 2);

    if (contains(boundsVertical, position) || contains(boundsHorizontal, position)) {
        return true;
    }

    vec2 mapped = mapInRectangle(bounds, v_position);
    vec2 coords = position - edgeCenter(bounds, mapped);
    float distance = r * r - (pow(coords.x, 2) + pow(coords.y, 2));
    g_radiusAlpha = smoothstep(distance, -g_radius / 2, 0);
    return distance > -g_radius / 2;
}

/**
* Returns the colour of the fragment
*
* @return the colour of the fragment
*/
vec4 getColour(void) {
    if (u_hasTexture) {
        vec4 colour = texture(u_texture, v_position);
        return mix(v_backgroundColour, colour, colour.a);
    }
    return v_backgroundColour * u_colourModifier;
}

/**
* Returns the discard map value of the fragment
*
* @return the discard map value of the fragment
*/
float getDiscardMapValue(void) {
    if (u_hasDiscardMap) {
        return texture(u_discardMap, v_position).r;
    }
    return 1.0;
}

/**
* Returns the border colour of the fragment
*
* @return the border colour of the fragment
*/
vec4 getBorderColour(void) {
    float r = ((u_borderColour << 0) >> 24);
    float g = ((u_borderColour << 8) >> 24);
    float b = ((u_borderColour << 16) >> 24);
    float a = ((u_borderColour << 24) >> 24);
    return vec4(r, g, b, a) / 255;
}


/*================ */
/*                 */
/*   MAIN METHOD   */
/*                 */
/*================ */

void main(void) {
    g_radius = getRadius(v_bounds, v_position);

    float discardMapValue = getDiscardMapValue();

    if (isInside(v_bounds)){
        vec4 colour = getColour();
        vec4 borderColour = getBorderColour();
        float blendFactor = g_radiusAlpha * g_radiusAlpha;

        fragColour = mix(borderColour, colour, blendFactor);
    }
    else if (isInside(v_borders)){
        vec4 borderColour = getBorderColour();
        float blendFactor = g_radiusAlpha * g_radiusAlpha;

        fragColour = borderColour * vec4(1, 1, 1, blendFactor);
    }
    else {
        discard;
    }

    fragColour.a *= u_opacity * discardMapValue;
}
