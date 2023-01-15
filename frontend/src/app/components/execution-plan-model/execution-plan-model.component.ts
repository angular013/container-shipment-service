import {Component, Input, OnInit} from '@angular/core';
import {Shipment, TransportType} from "../../models/shipment.mode";
import { ShipmentService } from '../../services/shipment.service';


@Component({
  selector: 'execution-plan-model',
  templateUrl: './execution-plan-model.component.html',
  styleUrls: ['./execution-plan-model.component.scss']
})
export class ExecutionPlanModelComponent implements OnInit {

  @Input()
  shipment: Shipment;

  selectedShipments: Shipment[] = [];
  selectedTemplate: any

  constructor(private shipmentService: ShipmentService) { }

  ngOnInit(): void {
    this.selectedShipments  = this.shipmentService.getSelectedShipments();
  }

  onSubmit() {
    if (this.selectedTemplate === 'General Shipment Template' && this.selectedShipments.length > 0) {
      this.shipmentService.postSelectedShipments(this.selectedShipments).subscribe(response => {
        //console.log(response);
      });
    }
    else{
      alert("No shipment/template selected ")
    }
  }

}
