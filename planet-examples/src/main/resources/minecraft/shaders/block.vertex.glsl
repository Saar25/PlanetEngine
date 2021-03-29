/**
* Block vertex shader
*/

// Consts
const vec2[] uvCoords = {
vec2(+0.0, +1.0), vec2(+0.0, +0.0),
vec2(+1.0, +0.0), vec2(+1.0, +1.0)
};

const int[] indexMap = {
0, 1, 2, 0, 2, 3
};

// Per Vertex attibutes
layout (location = 0) in uint in_data;

// Uniforms
uniform mat4 u_projectionView;
uniform ivec2 u_chunkCoordinate;
uniform ivec2 u_dimensions;

// Vertex outputs
flat out int v_id;
flat out int v_dir;
out vec2 v_uvCoords;
out vec3 v_position;

// Global variables
float g_x;
float g_y;
float g_z;
int g_id;
int g_dir;

// Methods declaration
void init_globals(void);

void main(void) {
    init_globals();

    v_id = g_id;
    v_dir = g_dir;

    vec2 uvCoordsOffset = vec2(g_id % u_dimensions.x, g_id / u_dimensions.y);
    v_uvCoords = (uvCoordsOffset + uvCoords[indexMap[gl_VertexID % 6]]) / u_dimensions;

    v_position = vec3(g_x, g_y, g_z);
    v_position.x += u_chunkCoordinate.x * 16;
    v_position.z += u_chunkCoordinate.y * 16;

    gl_Position = u_projectionView * vec4(v_position, 1.0);
}

// Methods
void init_globals(void) {
    g_x   = float(in_data >> 0x1Bu & 0x01Fu);
    g_z   = float(in_data >> 0x16u & 0x01Fu);
    g_y   = float(in_data >> 0x0Du & 0x1FFu);
    g_id  =   int(in_data >> 0x05u & 0x0FFu);
    g_dir =   int(in_data >> 0x02u & 0x007u);
}