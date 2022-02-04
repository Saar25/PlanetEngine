/**
* Water vertex shader
*/

// Consts
const vec2[] uvCoords = vec2[](
vec2(+0.0, +1.0), vec2(+0.0, +0.0),
vec2(+1.0, +0.0), vec2(+1.0, +1.0)
);

const int[] indexMap = int[](
0, 1, 2, 0, 2, 3
);

const vec3[] vertexMap = vec3[](
vec3(0, 0, 0), vec3(1, 0, 0),
vec3(0, 0, 1), vec3(1, 0, 1),
vec3(0, 1, 0), vec3(1, 1, 0),
vec3(0, 1, 1), vec3(1, 1, 1)
);

const vec3[] directions = vec3[](
vec3(+1, 0, 0),
vec3(-1, 0, 0),
vec3(0, +1, 0),
vec3(0, -1, 0),
vec3(0, 0, +1),
vec3(0, 0, -1)
);

const int[] vertexIds = int[] (
1, 5, 7, 3,
2, 6, 4, 0,
4, 6, 7, 5,
1, 3, 2, 0,
3, 7, 6, 2,
0, 4, 5, 1
);

const int TOP_DIR = 2;

// Per Vertex attibutes
layout (location = 0) in uint in_data;

// Uniforms
uniform mat4 u_projectionView;
uniform mat4 u_normalMatrix;
uniform ivec2 u_chunkCoordinate;
uniform ivec2 u_dimensions;

uniform int u_texturesCount;
uniform int u_transitionId;

// Vertex outputs
flat out int v_id;
flat out int v_dir;
flat out int v_shw;
flat out vec3 v_normal;
smooth out float v_ao;
out vec2 v_uvCoords1;
out vec2 v_uvCoords2;

// Global variables
float g_x;
float g_y;
float g_z;
int g_id;
int g_dir;
int g_shw;
float g_ao;

// Methods declaration
void init_globals(void);

void main(void) {
    init_globals();

    v_id = g_id;
    v_dir = g_dir;
    v_shw = g_shw;
    v_ao = g_ao;

    int index = indexMap[gl_VertexID % 6];

    if (g_dir == TOP_DIR) {
        int id1 = g_id + (u_transitionId % u_texturesCount);
        vec2 uvCoordsOffset1 = vec2(id1 % u_dimensions.x, id1 / u_dimensions.y);
        v_uvCoords1 = (uvCoordsOffset1 + uvCoords[index]) / u_dimensions;

        int id2 = g_id + ((u_transitionId + 1) % u_texturesCount);
        vec2 uvCoordsOffset2 = vec2(id2 % u_dimensions.x, id2 / u_dimensions.y);
        v_uvCoords2 = (uvCoordsOffset2 + uvCoords[index]) / u_dimensions;
    } else {
        vec2 uvCoordsOffset2 = vec2(g_id % u_dimensions.x, g_id / u_dimensions.y);
        v_uvCoords1 = (uvCoordsOffset2 + uvCoords[index]) / u_dimensions;
        v_uvCoords2 = v_uvCoords1;
    }

    ivec3 block = ivec3(g_x, g_y, g_z);
    block.x += u_chunkCoordinate.x * 16;
    block.z += u_chunkCoordinate.y * 16;

    int vId = vertexIds[g_dir * 4 + index];
    vec3 position = block + vertexMap[vId];

    v_normal = (u_normalMatrix * vec4(directions[v_dir], 0)).xyz;

    gl_Position = u_projectionView * vec4(position, 1.0);
}

// Methods
void init_globals(void) {
    g_x   = float(in_data >> 0x1Cu & 0x0Fu);
    g_z   = float(in_data >> 0x18u & 0x0Fu);
    g_y   = float(in_data >> 0x10u & 0xFFu);
    g_id  =   int(in_data >> 0x08u & 0xFFu);
    g_dir =   int(in_data >> 0x05u & 0x07u);
    g_shw =   int(in_data >> 0x01u & 0x0Fu);
    g_ao =  float(in_data >> 0x00u & 0x01u);
}