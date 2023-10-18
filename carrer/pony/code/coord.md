
# convert 2d vcs, wcs

const double x_index_in_vcs =
      (engaging_toll_gate - vehicle_position_).InnerProd(vehicle_heading_);
  const double y_index_in_vcs =
      (engaging_toll_gate - vehicle_position_).CrossProd(vehicle_heading_);
  const math::Vec2d toll_gate_position_in_vcs(x_index_in_vcs, y_index_in_vcs);
  return toll_gate_position_in_vcs;




## fcn ics

// Convert world position x/y to image coordinate col/row.
// This is an illustration of the world coordinate system and the image coordinate system
// when the vehicle position is at the world origin and its heading is 0.
//
//                              ^ world_y_axis
//                              |
//      image_origin            |
//               o--------------|--------------> image_x_axis
//               |              |              |
//               |              |              |
//  ----------------------------|----------------------------> world_x_axis
//               |              |              |
//               |              |              |
//  image_y_axis v--------------|--------------.
//                              |
//                              |
//                              |
//



## wcs

use estimated ground in DrivingAreaTypeDetectorUnit when set FLAGS_disallow_use_of_roadgraph_z i