/**
* Water vertex shader
*/

// Consts
const vec2[] uvCoords = {
vec2(+0.0, +1.0), vec2(+0.0, +0.0),
vec2(+1.0, +0.0), vec2(+1.0, +1.0)
};

const int[] indexMap = {
0, 1, 2, 0, 2, 3
};

const vec3[] vertexMap = {
vec3(0, 0, 0), vec3(1, 0, 0),
vec3(0, 0, 1), vec3(1, 0, 1),
vec3(0, 1, 0), vec3(1, 1, 0),
vec3(0, 1, 1), vec3(1, 1, 1),
};

const int[] directionMap = {
5, 3, 1, 4, 2, 0, -1, -1
};

const int TOP_DIR = 2;

// Per Vertex attibutes
layout (location = 0) in uint in_data;

// Uniforms
uniform mat4 u_projectionView;
uniform ivec2 u_chunkCoordinate;
uniform ivec2 u_dimensions;

uniform int u_texturesCount;
uniform int u_transitionId;

// Vertex outputs
flat out int v_id;
flat out int v_dir;
out vec2 v_uvCoords1;
out vec2 v_uvCoords2;

// Global variables
float g_x;
float g_y;
float g_z;
int g_id;
int g_vId;
int g_tex;

// Methods declaration
void init_globals(void);

void main(void) {
    init_globals();

    v_id = g_id;
    v_dir = directionMap[g_vId];

    int transitionId = v_dir == TOP_DIR ? u_transitionId : 0;

    int uvCoordIndex = ((indexMap[(gl_VertexID) % 6] + g_tex) % 4);

    int id1 = g_id + transitionId % u_texturesCount;
    vec2 uvCoordsOffset1 = vec2(id1 % u_dimensions.x, g_id / u_dimensions.y);
    v_uvCoords1 = (uvCoordsOffset1 + uvCoords[uvCoordIndex]) / u_dimensions;

    int id2 = g_id + (transitionId + 1) % u_texturesCount;
    vec2 uvCoordsOffset2 = vec2(id2 % u_dimensions.x, g_id / u_dimensions.y);
    v_uvCoords2 = (uvCoordsOffset2 + uvCoords[uvCoordIndex]) / u_dimensions;

    vec3 position = vec3(g_x, g_y, g_z) + vertexMap[g_vId];
    position.x += u_chunkCoordinate.x * 16;
    position.z += u_chunkCoordinate.y * 16;

    gl_Position = u_projectionView * vec4(position, 1.0);
}

// Methods
void init_globals(void) {
    g_x   = float(in_data >> 0x1Cu & 0x0Fu);
    g_z   = float(in_data >> 0x18u & 0x0Fu);
    g_y   = float(in_data >> 0x10u & 0xFFu);
    g_id  =   int(in_data >> 0x08u & 0xFFu);
    g_vId =   int(in_data >> 0x05u & 0x07u);
    g_tex =   int(in_data >> 0x04u & 0x01u);
}