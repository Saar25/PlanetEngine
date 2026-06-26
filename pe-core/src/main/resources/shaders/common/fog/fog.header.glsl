/**
*
* Fog Header file
*
**/

float calcFogAmount(float distance, Fog fog);

vec3 calcFogColor(float distance, Fog fog);

vec3 applyFogColor(float distance, Fog fog, vec3 color);