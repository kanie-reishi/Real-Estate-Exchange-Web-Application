/*** REGION 1 - Global variables - Vùng khai báo biến, hằng số, tham số TOÀN CỤC */
import { Uppy, DragDrop, XHRUpload, Dashboard } from "https://releases.transloadit.com/uppy/v3.26.1/uppy.min.mjs"
const uppy = new Uppy({
    restrictions: {
        maxFileSize: 10 * 1024 * 1024, // 10MB
        // ... other restrictions
    },
    autoProceed: true,
})
    .use(Dashboard, {
        height: 350,
        inline: true,
        target: '#drag-drop-area',
        hideUploadButton: true,
        locale: {
            strings: {
                addMoreFiles: 'Thêm ảnh',
                addingMoreFile: 'Đang thêm ảnh',
                dashboardTitle: 'Upload ảnh',
                back: 'Quay lại',
                removeFile: 'Xóa ảnh',
                dropHint: 'Kéo thả ảnh vào đây',
                uploadComplete: 'Tải lên thành công',
                uploadPaused: 'Tải lên tạm dừng',
                resumeUpload: 'Tiếp tục tải lên',
                pauseUpload: 'Tạm dừng tải lên',
                cancelUpload: 'Hủy tải lên',
                xFilesSelected: {
                    0: '%{smart_count} ảnh đã chọn',
                    1: '%{smart_count} ảnh đã chọn'
                },
                uploadingXFiles: {
                    0: 'Đang tải lên %{smart_count} ảnh',
                    1: 'Đang tải lên %{smart_count} ảnh'
                },
                processingXFiles: {
                    0: 'Đang xử lý %{smart_count} ảnh',
                    1: 'Đang xử lý %{smart_count} ảnh'
                },
                poweredBy: '',
                addMore: 'Thêm ảnh',
                save: 'Lưu',
                cancel: 'Hủy',
                dropPasteFiles: 'Kéo thả ảnh vào đây hoặc %{browseFiles}',
                dropPasteFolders: 'Kéo thả ảnh vào đây hoặc %{browseFolders}',
                dropPasteBoth: 'Kéo thả ảnh hoặc %{browseFiles} hoặc %{browseFolders}',
                browseFiles: 'chọn từ máy tính',
                browseFolders: 'chọn từ thư mục',
            }
        },
        proudlyDisplayPoweredByUppy: false,
    })
    .use(XHRUpload, { endpoint: 'http://localhost:8080/photo/upload' });
// Listen on file added event
uppy.on('file-added', (file) => {
    const url = URL.createObjectURL(file.data);
    const img = new Image();
    img.onload = function () {
        URL.revokeObjectURL(url);
        // Check if image is less than 300x300
        if (this.width < 300 || this.height < 300) {
            uppy.removeFile(file.id);
            alert('Kích thước ảnh tối thiểu 300x300px');
        }
    };
    img.src = url;
});
// Listen on upload success event
uppy.on('upload-success', (file, response) => {
     // Get the file URL from the response
     var fileId = response.body.data.id;

     // Store the file URL in localStorage
    var storedFiles = JSON.parse(localStorage.getItem('storedFiles')) || [];
});