<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import TRAFFIC_LIGHT from "@/components/objects/TRAFFIC_LIGHT.vue";
import { ObjectEnum } from "@/services/ObjectEnum";
import STREET_T_CROSS_URL from "@/assets/models/STREET_T_CROSS.glb?url";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: any;
    orientation: string;
  }>(),
  { width: 10, height: 10 }
);
const trafficLightLeft = "trafficLightLeft";
const trafficLightRight = "trafficLightRight";
const trafficLightStraight = "trafficLightStraight";

function setPosition(orientation: string, name: string): THREE.Vector3 {
  if (orientation == "WEST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(3, 0, 2);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(2, 0, -3);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(-3, 0, -1);
    }
  } else if (orientation == "NORTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(3, 0, 2);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(1, 0, -3);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(-2, 0, 3);
    }
  } else if (orientation == "SOUTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 3);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(-3, 0, -2);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(3, 0, -2);
    }
  } else if (orientation == "EAST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(3, 0, 1);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(-3, 0, -2);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(-2, 0, 3);
    }
  }
  return new THREE.Vector3(0, 0, 0);
}

function setRotation(orientation: string, name: string): THREE.Vector3 {
  if (orientation == "WEST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 1.57, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 3.14, 0);
    }
  } else if (orientation == "NORTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 1.57, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 4.71, 0);
    }
  } else if (orientation == "SOUTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 4.71, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 3.14, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 1.75, 0);
    }
  } else if (orientation == "EAST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 3.14, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 4.71, 0);
    }
  }
  return new THREE.Vector3(0, 0, 0);
}
</script>
<template>
  <GltfModel
    ref="model"
    :src="STREET_T_CROSS_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightLeft))
    "
    :rotation="setRotation(props.orientation, trafficLightLeft)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightRight))
    "
    :rotation="setRotation(props.orientation, trafficLightRight)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightStraight))
    "
    :rotation="setRotation(props.orientation, trafficLightStraight)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
</template>
