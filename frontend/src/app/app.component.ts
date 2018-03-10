import { Component, OnInit } from '@angular/core';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  constructor(private sessionService: SessionService) {}

  ngOnInit() {
    this.sessionService.getUser().subscribe(user => {
      this.title = this.title + ', ' + user.value;
    });
  }

}
