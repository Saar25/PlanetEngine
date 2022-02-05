/**
* Block vertex shader
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

// Per Vertex attibutes
layout (location = 0) in uint in_data;

// Uniforms
uniform mat4 u_projectionView;
uniform mat4 u_normalMatrix;
uniform ivec2 u_chunkCoordinate;
uniform ivec2 u_dimensions;

// Vertex outputs
flat out int v_id;
flat out int v_dir;
flat out int v_lit;
flat out vec3 v_normal;
flat out ivec3 v_block;
smooth out float v_ao;
out vec2 v_uvCoords;
out vec3 v_position;

// Global variables
float g_x;
float g_y;
float g_z;
int g_id;
int g_dir;
int g_lit;
float g_ao;

// Methods declaration
void init_globals(void);

void main(void) {
    init_globals();

    v_id = g_id;
    v_dir = g_dir;
    v_lit = g_lit;
    v_ao = g_ao;

    int index = indexMap[gl_VertexID % 6];

    vec2 uvCoordsOffset = vec2(g_id % u_dimensions.x, g_id / u_dimensions.y);
    v_uvCoords = (uvCoordsOffset + uvCoords[(index) % 4]) / u_dimensions;

    v_block = ivec3(g_x, g_y, g_z);
    v_block.x += u_chunkCoordinate.x * 16;
    v_block.z += u_chunkCoordinate.y * 16;

    int vId = vertexIds[g_dir * 4 + index];
    v_position = v_block + vertexMap[vId];

    v_normal = (u_normalMatrix * vec4(directions[v_dir], 0)).xyz;

    gl_Position = u_projectionView * vec4(v_position, 1.0);
}

// Methods
void init_globals(void) {
    g_x   = float(in_data >> 0x1Cu & 0x0Fu);
    g_z   = float(in_data >> 0x18u & 0x0Fu);
    g_y   = float(in_data >> 0x10u & 0xFFu);
    g_id  =   int(in_data >> 0x08u & 0xFFu);
    g_dir =   int(in_data >> 0x05u & 0x07u);
    g_lit =   int(in_data >> 0x01u & 0x0Fu);
    g_ao  = float(in_data >> 0x00u & 0x01u);
}