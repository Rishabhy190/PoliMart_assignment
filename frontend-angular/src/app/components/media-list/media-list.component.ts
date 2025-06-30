import { Component, OnInit } from '@angular/core';
import { MediaService } from '../../services/media.service';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-media-list',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './media-list.component.html',
  styleUrls: ['./media-list.component.css']
})
export class MediaListComponent implements OnInit {
  mediaList: any[] = [];
  errorMessage: string = '';

  constructor(private mediaService: MediaService) { }

  ngOnInit(): void {
    this.loadMedia();
  }

  loadMedia(): void {
    this.mediaService.list().subscribe({
      next: (data) => this.mediaList = data,
      error: (err) => this.errorMessage = 'Failed to load media.'
    });
  }

  downloadMedia(media: any): void {
    this.mediaService.download(media.id).subscribe(blob => {
      const a = document.createElement('a');
      const objectUrl = URL.createObjectURL(blob);
      a.href = objectUrl;
      a.download = media.originalFileName;
      a.click();
      URL.revokeObjectURL(objectUrl);
    });
  }

  formatBytes(bytes: number, decimals = 2): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }
} 