import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MediaService } from '../../services/media.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-media-upload',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './media-upload.component.html',
  styleUrls: ['./media-upload.component.css']
})
export class MediaUploadComponent {
  uploadForm: FormGroup;
  selectedFile: File | null = null;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private fb: FormBuilder, private mediaService: MediaService) {
    this.uploadForm = this.fb.group({
      file: [null],
      description: ['']
    });
  }

  onFileChange(event: any): void {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  onSubmit(): void {
    if (this.selectedFile) {
      const description = this.uploadForm.get('description')?.value;
      this.mediaService.upload(this.selectedFile, description).subscribe({
        next: (res) => {
          this.successMessage = 'File uploaded successfully!';
          this.errorMessage = '';
          this.uploadForm.reset();
          this.selectedFile = null;
        },
        error: (err) => {
          this.errorMessage = 'File upload failed. Please try again.';
          this.successMessage = '';
        }
      });
    }
  }
} 